package com.ibm.jaql.benchmark.fs;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.permission.FsPermission;

public class MemoryFsFileNullWrite extends MemoryFsFile {

	public MemoryFsFileNullWrite(String name, MemoryFsDirectory parent,
			int bufferSize, short replication, FsPermission permission,
			long blockSize) {
		super(name, parent, bufferSize, replication, permission, blockSize);
	}
	
	public MemoryFsFileNullWrite(String name, MemoryFsDirectory parent) {
		super(name, parent, 64*1024*1024, (short) 1, FsPermission.getDefault(), 64*1024*1024);
	}

	@Override
	public FSDataInputStream getInputStream(int bufferSize) {
		throw new RuntimeException("Reading not supported");
	}
	
	@Override
	public FSDataOutputStream getOutputStream() {
		try {
			return new FSDataOutputStream(new NullOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	class NullOutputStream extends OutputStream {				
		@Override
		public void write(int arg0) throws IOException { length += 1;}
		
		@Override
	    public void write(byte[] b, int off, int len) throws IOException { length += len; }
	};
}
