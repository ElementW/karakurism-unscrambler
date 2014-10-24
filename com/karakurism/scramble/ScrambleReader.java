package com.karakurism.scramble;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ScrambleReader
{
  private InputStream mStream;
  private ScrambleDataInfo mDataInfo;
  private long mKey = 0L;

  public ScrambleReader(InputStream stream, int key)
  {
    if (stream == null)
      throw new AssertionError();
    this.mStream = stream;
    this.mKey = key;
  }

  private byte[] unscramble(byte[] data)
  {
    if (data == null)
      throw new AssertionError();
    if (data.length <= 0)
      throw new AssertionError();
    for (int i = 0; i < data.length; i++)
    {
      this.mKey = (0xFFFF & 2531011L + 214013L * this.mKey >> 16);
      data[i] = ((byte)(int)(data[i] ^ this.mKey));
    }
    return data;
  }

  public ScrambleDataInfo getScrambleInfo()
  {
    if (this.mStream == null)
      throw new AssertionError();
    if (this.mDataInfo != null)
      throw new AssertionError();
    this.mDataInfo = new ScrambleDataInfo();
    try
    {
      byte[] hdrArr = new byte[2];
      this.mStream.read(hdrArr, 0, 2);
      this.mDataInfo.header = BytewiseHelper.getIntFrom2(unscramble(hdrArr));
      byte[] lenArr = new byte[8];
      this.mStream.read(lenArr, 0, 8);
      this.mDataInfo.dataLength = BytewiseHelper.getLongFrom8(unscramble(lenArr));
    }
    catch (IOException e)
    {
      // snap
    }
    return this.mDataInfo;
  }

  public void unscramble(OutputStream outStream, byte[] buffer, long size)
  {
    if (outStream == null)
      throw new AssertionError();
    if (this.mDataInfo == null)
      throw new AssertionError();
    long total = 0L;
    try
    {
      do
      {
        int read = this.mStream.read(buffer);
        if (read < 0)
          return;
        outStream.write(unscramble(buffer), 0, read);
        total += read;
      }
      while (total < size);
    }
    catch (IOException e)
    {
      // try again
    }
  }
}