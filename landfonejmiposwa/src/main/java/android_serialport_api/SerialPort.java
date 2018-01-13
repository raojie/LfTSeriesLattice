/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android_serialport_api;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class SerialPort {

	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	private int mIntFd;
	private String serialName;

	public SerialPort(File device, int baudrate, char parity, int flags) throws SecurityException, IOException {

		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/xbin/su");
				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
						+ "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !device.canRead()
						|| !device.canWrite()) {
					throw new SecurityException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		mFd = open(device.getAbsolutePath(), baudrate, parity, flags);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}

		Log.d("Serial", "mFd[" + mFd + "]");
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
	}

	public SerialPort(String device, int baudrate, char parity, int flags) throws SecurityException, IOException {
		serialName = device;
		File fileDevice = new File("/dev/" + device);
		if(!fileDevice.exists()) {
			throw new IOException();
		}

		/* Check access permission */
		if (!fileDevice.canRead() || !fileDevice.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/xbin/su");
				String cmd = "chmod 666 " + fileDevice.getAbsolutePath() + "\n"
						+ "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !fileDevice.canRead()
						|| !fileDevice.canWrite()) {
					throw new SecurityException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		mIntFd = openFd(fileDevice.getAbsolutePath(), baudrate, parity, flags);
		if (-1 == mIntFd) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}

		Log.d("Serial", "mFd[" + mIntFd + "]");
	}

	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	public int jRead(byte[] buffer, int size, int timeout) {
		if(-1 == mIntFd) return 0;
		return read(mIntFd, buffer, size, timeout);
	}

	public int jWrite(byte[] buffer, int size, int timeout) {
		if(-1 == mIntFd) return 0;
		return write(mIntFd, buffer, size, timeout);
	}

	public void closeFd() {
		if(-1 != mIntFd) {
			closeFd(mIntFd);
		}
	}

	public String getSerialName() {
		return serialName;
	}

	public int getmIntFd() {
		return mIntFd;
	}

	// JNI
	private native static FileDescriptor open(String path, int baudrate, char parity, int flags);
	private native static int openFd(String path, int baudrate, char parity, int flags);
	public native void close();
	public native void closeFd(int fd);
	public native int read(int fd, byte[] buffer, int size, int timeout);
	public native int write(int fd, byte[] buffer, int size, int timeout);
	static {
		System.loadLibrary("serial_port");
	}
}
