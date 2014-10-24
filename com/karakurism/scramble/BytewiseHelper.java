package com.karakurism.scramble;

class BytewiseHelper
{
  static int getIntFrom2(byte[] data)
  {
    if (data == null)
      throw new AssertionError();
    if (data.length < 2)
      throw new AssertionError();
    return (0xFF & data[0]) << 8 | 0xFF & data[1];
  }

  static long getLongFrom8(byte[] data)
  {
    if (data == null)
      throw new AssertionError();
    if (data.length < 8)
      throw new AssertionError();
    return (0xFF & data[0]) << 56 | (0xFF & data[1]) << 48 | (0xFF & data[2]) << 40 | (0xFF & data[3]) << 32 | (0xFF & data[4]) << 24 | (0xFF & data[5]) << 16 | (0xFF & data[6]) << 8 | 0xFF & data[7];
  }
}