package lc.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.springframework.util.Assert;


/**
 * {@link InputStream}, {@link OutputStream}을 통해 byte를 읽고 쓰기 위한 method를 제공하는
 * Utility class이다.
 * 
 * @author <a href="mailto:shshim@crossent.com">ShimSangho</a>
 * @version 0.1.1
 * @since 2010-01-01
 * @revision 2010-01-01, ShimSangho
 * 
 */
public class ByteUtil { 

	/**
	 * Alignment.
	 * 
	 * @author <a href="mailto:shshim@crossent.com">ShimSangho</a>
	 * @version 0.1.1
	 * @since 2010-01-01
	 * @revision 2010-01-01, ShimSangho
	 * 
	 */
	public static enum Align {
		/** Left */
		Left,
		/** Right */
		Right;
	}

	/**
	 * Endian.
	 * 
	 * @author <a href="mailto:shshim@crossent.com">ShimSangho</a>
	 * @version 0.1.1
	 * @since 2010-01-01
	 * @revision 2010-01-01, ShimSangho
	 * 
	 */
	public static enum Endian {
		/** Little Endian */
		Little,
		/** Big Endian */
		Big;
	}
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final byte[] NULL_BYTE_ARRAY = new byte[0];

	public static final byte SPACE = 32;

	public static final byte ZERO = 48;

	public static final byte NINE = 57;

	public static final byte FULL_STOP = 46;

	public static final byte COMMA = 44;
	
    private static final int MAX_RETRY_COUNT = 1;

    /**
	 * <code>byte</code> array <code>b</code>를 <code>length</code> 길이만큼
	 * <code>align</code>에 맞춰 쓴다.
	 * <p>
	 * <code>b</code>의 길이보다 <code>length</code>가 긴 경우, 남는 부분은
	 * <code>padding</code>으로 채운다.
	 * 
	 * @param b
	 *            <code>byte<code> array.
	 * @param length
	 *            쓸 길이.
	 * @param align
	 *            채울 방향.
	 * @param padding
	 *            빈 공간을 채울 <code>byte</code>.
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws IOException
	 *             {@link OutputStream}에 <code>byte</code> array를 쓰는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>b</code>의 길이가 <code>length</code>보다 긴 경우.
	 *             <code>align</code>이 <code>null</code>인 경우.
	 */
	public static void paddingWrite(byte[] b, int length, Align align,
			byte padding, OutputStream outputStream) throws IOException {
		Assert.isTrue(b.length <= length, "byte's length(= " + b.length
				+ ") must be smaller than length(= " + length + ").");
		switch (align) {
		case Left:
			outputStream.write(b);
			for (int i = 0; i < length - b.length; i++) {
				outputStream.write(padding);
			}
			break;
		case Right:
			for (int i = 0; i < length - b.length; i++) {
				outputStream.write(padding);
			}
			outputStream.write(b);
			break;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
	}

	/**
	 * <code>length</code>길이만큼 <code>byte</code> array를 읽어서 그 중
	 * <code>align</code>에 맞춰 <code>padding</code>으로 채워진 부분을 제외한 나머지
	 * <code>byte</code> array를 return한다.
	 * 
	 * @param length
	 *            읽을 길이.
	 * @param align
	 *            채운 방향.
	 * @param padding
	 *            빈 공간을 채운 <code>byte</code>.
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return <code>padding</code>을 제외한 실제 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 */
	public static byte[] paddingRead(int length, Align align, byte padding,
			InputStream inputStream) throws IOException {
		byte[] bytes = read(length, inputStream);
		int i;
		byte[] trimed = null;

		switch (align) {
		case Left:
			i = length - 1;
			while (0 <= i) {
				if (((byte) bytes[i]) != padding) {
					break;
				}
				i--;
			}
			trimed = new byte[i + 1];
			System.arraycopy(bytes, 0, trimed, 0, trimed.length);
			break;
		case Right:
			i = 0;
			while (i < length) {
				if (((byte) bytes[i]) != padding) {
					break;
				}
				i++;
			}
			trimed = new byte[length - i];
			System.arraycopy(bytes, i, trimed, 0, trimed.length);
			break;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
		return trimed;
	}

	public static byte[] read(int length, InputStream inputStream)
			throws IOException {
        int bcount = 0, n = 0, read_retry_count = 0;
        byte[] bytes = new byte[length];
        while(bcount < length) {
            n = inputStream.read(bytes, bcount, length - bcount);
            if(n > 0)
            	bcount += n;
            else if(n == -1)
            	throw new IOException("inputstream has returned an unexpected EOF");
            else if(n == 0 && ++read_retry_count == MAX_RETRY_COUNT)
    			throw new IOException(
    					"Not Enough Bytes in InputStream : required = " + length
    							+ ", actual = " + bcount);
        }

        return bytes;
	}

	/**
	 * <code>String</code> 값 <code>value</code>가 <code>null</code>인 경우,
	 * <code>length</code>만큼 <code>padding</code> 으로 채우는
	 * {@link #paddingWrite(byte[], int, Align, byte, OutputStream)} method.
	 * 
	 * @param value
	 *            <code>String</code> 값.
	 * @param length
	 *            쓸 길이.
	 * @param align
	 *            채울 방향.
	 * @param padding
	 *            빈 공간을 채울 <code>byte</code>.
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws IOException
	 *             {@link OutputStream}에 <code>byte</code> array를 쓰는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 * @see #paddingWrite(byte[], int, Align, byte, OutputStream)
	 */
	public static void nullablePaddingWrite(String value, int length,
			Align align, byte padding, OutputStream outputStream)
			throws IOException {
		if (value == null) {
			paddingWrite(NULL_BYTE_ARRAY, length, align, padding, outputStream);
		} else {
			paddingWrite(value.getBytes(), length, align, padding, outputStream);
		}
	}

	/**
	 * <code>String</code> 값 <code>value</code>가 <code>null</code>인 경우,
	 * <code>length</code>만큼 <code>padding</code> 으로 채우는
	 * {@link #paddingWrite(byte[], int, Align, byte, OutputStream)} method.
	 * <p>
	 * <code>value</code>가 <code>null</code>이 아닌 경우, <code>charset</code>을 사용하여
	 * <code>byte</code> array를 얻는다.
	 * 
	 * @param value
	 *            <code>String</code> 값.
	 * @param charset
	 *            Character Encoding.
	 * @param length
	 *            쓸 길이.
	 * @param align
	 *            채울 방향.
	 * @param padding
	 *            빈 공간을 채울 <code>byte</code>.
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws IOException
	 *             {@link OutputStream}에 <code>byte</code> array를 쓰는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 * @throws java.io.UnsupportedEncodingException
	 *             <code>charset</code>이 올바르지 않은 경우.
	 * @see String#getBytes(String)
	 */
	public static void nullablePaddingWrite(String value, String charset,
			int length, Align align, byte padding, OutputStream outputStream)
			throws IOException {
		if (value == null) {
			paddingWrite(NULL_BYTE_ARRAY, length, align, padding, outputStream);
		} else {
			paddingWrite(value.getBytes(charset), length, align, padding,
					outputStream);
		}
	}

	/**
	 * <code>String</code> 값 <code>value</code>가 <code>null</code>인 경우,
	 * <code>length</code>만큼 <code>padding</code> 으로 채우는
	 * {@link #paddingWrite(byte[], int, Align, byte, OutputStream)} method.
	 * <p>
	 * <code>value</code>가 <code>null</code>이 아닌 경우, <code>charset</code>을 사용하여
	 * <code>byte</code> array를 얻는다.
	 * 
	 * @param value
	 *            String 값.
	 * @param charset
	 *            {@link Charset}.
	 * @param length
	 *            쓸 길이.
	 * @param align
	 *            채울 방향.
	 * @param padding
	 *            빈 공간을 채울 <code>byte</code>.
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws IOException
	 *             {@link OutputStream}에 <code>byte</code> array를 쓰는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 * @throws java.io.UnsupportedEncodingException
	 *             <code>charset</code>이 올바르지 않은 경우.
	 * @see String#getBytes(Charset)
	 */
	public static void nullablePaddingWrite(String value, Charset charset,
			int length, Align align, byte padding, OutputStream outputStream)
			throws IOException {
		if (value == null) {
			paddingWrite(NULL_BYTE_ARRAY, length, align, padding, outputStream);
		} else {
			paddingWrite(value.getBytes(charset), length, align, padding,
					outputStream);
		}
	}

	/**
	 * 읽은 <code>byte</code> array의 길이가 0인 경우 <code>null</code>을 return하는
	 * {@link #paddingRead(int, Align, byte, InputStream)} method.
	 * 
	 * @param length
	 *            읽을 길이.
	 * @param align
	 *            채운 방향.
	 * @param padding
	 *            빈 공간을 채운 <code>byte</code>.
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return <code>padding</code>을 제외한 실제 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 */
	public static String nullablePaddingRead(int length, Align align,
			byte padding, InputStream inputStream) throws IOException {
		byte[] bytes = paddingRead(length, align, padding, inputStream);
		if (bytes.length == 0) {
			return null;
		} else {
			return new String(bytes);
		}
	}

	/**
	 * 읽은 <code>byte</code> array의 길이가 0인 경우 <code>null</code>을 return하는
	 * {@link #paddingRead(int, Align, byte, InputStream)} method.
	 * <p>
	 * String을 생성할 때 <code>charset</code>을 사용한다.
	 * 
	 * @param charset
	 *            Character Encoding.
	 * @param length
	 *            읽을 길이.
	 * @param align
	 *            채운 방향.
	 * @param padding
	 *            빈 공간을 채운 <code>byte</code>.
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return <code>padding</code>을 제외한 실제 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 * @throws java.io.UnsupportedEncodingException
	 *             <code>charset</code>이 올바르지 않은 경우.
	 * @see String#String(byte[], String) String(byte[], String)
	 */
	public static String nullablePaddingRead(String charset, int length,
			Align align, byte padding, InputStream inputStream)
			throws IOException {
		byte[] bytes = paddingRead(length, align, padding, inputStream);
		if (bytes.length == 0) {
			return null;
		} else {
			return new String(bytes, charset);
		}
	}

	/**
	 * 읽은 <code>byte</code> array의 길이가 0인 경우 <code>null</code>을 return하는
	 * {@link #paddingRead(int, Align, byte, InputStream)} method.
	 * <p>
	 * String을 생성할 때 <code>charset</code>을 사용한다.
	 * 
	 * @param charset
	 *            {@link Charset}.
	 * @param length
	 *            읽을 길이.
	 * @param align
	 *            채운 방향.
	 * @param padding
	 *            빈 공간을 채운 <code>byte</code>.
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return <code>padding</code>을 제외한 실제 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>align</code>이 <code>null</code>인 경우.
	 * @throws java.io.UnsupportedEncodingException
	 *             <code>charset</code>이 올바르지 않은 경우.
	 * @see String#String(byte[], Charset) String(byte[], Charset)
	 */
	public static String nullablePaddingRead(Charset charset, int length,
			Align align, byte padding, InputStream inputStream)
			throws IOException {
		byte[] bytes = paddingRead(length, align, padding, inputStream);
		if (bytes.length == 0) {
			return null;
		} else {
			return new String(bytes, charset);
		}
	}

	/**
	 * <code>int</code> 값을 <code>endian</code>에 맞게 쓴다.
	 * 
	 * @param value
	 *            <code>int</code> 값.
	 * @param endian
	 *            {@link Endian}.
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws IOException
	 *             {@link OutputStream}에 <code>byte</code> array를 쓰는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>endian</code>이 <code>null</code>인 경우.
	 */
	public static void writeInt(int value, Endian endian,
			OutputStream outputStream) throws IOException {
		byte[] bytes = getBytes(value);
		switch (endian) {
		case Little:
			byte[] newBytes = new byte[] { bytes[3], bytes[2], bytes[1],
					bytes[0] };
			bytes = newBytes;
			break;
		case Big:
			// Nothing to do
			break;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
		outputStream.write(bytes);
	}

	/**
	 * <code>int<code> 값을 <code>endian</code>에 맞게 읽는다.
	 * 
	 * @param endian
	 *            {@link Endian}.
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return 읽어온 <code>int</code> 값.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 * @throws IllegalArgumentException
	 *             <code>endian</code>이 <code>null</code>인 경우.
	 */
	public static int readInt(Endian endian, InputStream inputStream)
			throws IOException {
		byte[] bytes = new byte[4];
		inputStream.read(bytes);
		switch (endian) {
		case Little:
			byte[] newBytes = new byte[] { bytes[3], bytes[2], bytes[1],
					bytes[0] };
			bytes = newBytes;
			break;
		case Big:
			// Nothing to do
			break;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
		return getInt(bytes);
	}

	/**
	 * <code>boolean</code>값 <code>true</code>를 <code>1</code>,
	 * <code>false</code>를 <code>0</code>으로 하여 <code>boolean</code> array를
	 * <code>byte</code> array로 변환한다.
	 * 
	 * @param values
	 *            <code>boolean</code> array.
	 * @param align
	 *            채울 방향.
	 * @return 변환된 <code>byte</code> array.
	 * @throws IllegalArgumentException
	 *             <code>values</code>가 <code>null</code>이거나 빈 array인 경우.
	 */
	public static byte[] getBytes(boolean[] values, Align align) {
		Assert.notNull(values, "values must not be null.");
		Assert.isTrue(values.length > 0, "values has elements.");

		int bytesLength = (values.length + 7) / 8;
		byte[] bytes = new byte[bytesLength];
		int bitPosition = 0;
		int bytePosition = 0;
		byte currentByte = 0x00;

		switch (align) {
		case Left:
			for (boolean value : values) {
				currentByte = (byte) ((currentByte << 1) | (value ? 0x01 : 0x00));
				bitPosition++;
				if (bitPosition == 8) {
					bytes[bytePosition] = currentByte;
					bytePosition++;
					currentByte = 0x00;
					bitPosition = 0;
				}
			}
			if (bytePosition < bytesLength) {
				while (bitPosition < 8) {
					currentByte = (byte) (currentByte << 1);
					bitPosition++;
				}
				bytes[bytePosition] = currentByte;
				bytePosition++;
				bitPosition = 0;
			}
			break;
		case Right:
			bitPosition = 8 - (values.length % 8);
			for (boolean value : values) {
				currentByte = (byte) ((currentByte << 1) | (value ? 0x01 : 0x00));
				bitPosition++;
				if (bitPosition == 8) {
					bytes[bytePosition] = currentByte;
					bytePosition++;
					currentByte = 0x00;
					bitPosition = 0;
				}
			}
			break;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
		Assert.isTrue(bytePosition == bytesLength, "Malfunctioned");
		Assert.isTrue(bitPosition == 0, "Malfunctioned");

		return bytes;
	}

	/**
	 * <code>short</code> 값을 <code>byte</code> array로 변환한다.
	 * 
	 * @param value
	 *            <code>short</code> 값.
	 * @return 변환된 <code>byte</code> array.
	 */
	public static byte[] getBytes(short value) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) ((value >> 8) & 0x000000ff);
		bytes[1] = (byte) (value & 0x000000ff);

		return bytes;
	}

	/**
	 * <code>int</code> 값을 <code>byte</code> array로 변환한다.
	 * 
	 * @param value
	 *            <code>int</code> 값.
	 * @return 변환된 <code>byte</code> array.
	 */
	public static byte[] getBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((value >> 24) & 0x000000ff);
		bytes[1] = (byte) ((value >> 16) & 0x000000ff);
		bytes[2] = (byte) ((value >> 8) & 0x000000ff);
		bytes[3] = (byte) (value & 0x000000ff);

		return bytes;
	}

	/**
	 * <code>long</code> 값을 <code>byte</code> array로 변환한다.
	 * 
	 * @param value
	 *            <code>long</code> 값.
	 * @return 변환된 <code>byte</code> array.
	 */
	public static byte[] getBytes(long value) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) ((value >> 56) & 0x000000ff);
		bytes[1] = (byte) ((value >> 48) & 0x000000ff);
		bytes[2] = (byte) ((value >> 40) & 0x000000ff);
		bytes[3] = (byte) ((value >> 32) & 0x000000ff);
		bytes[4] = (byte) ((value >> 24) & 0x000000ff);
		bytes[5] = (byte) ((value >> 16) & 0x000000ff);
		bytes[6] = (byte) ((value >> 8) & 0x000000ff);
		bytes[7] = (byte) (value & 0x000000ff);

		return bytes;
	}

	/**
	 * <code>boolean</code>값 <code>true</code>를 <code>1</code>,
	 * <code>false</code>를 <code>0</code>으로 하여 <code>byte</code> array에서
	 * <code>boolean</code> array를 읽어온다.
	 * 
	 * @param bytes
	 *            <code>byte</code> array.
	 * @param booleansLength
	 *            읽어올 <boolean> 값의 길이.
	 * @param align
	 *            채운 방향.
	 * @return <code>boolean</code> array.
	 * @throws IllegalArgumentException
	 *             <code>bytes</code>가 <code>null</code>인 경우.
	 *             <code>booleansLength</code> 값이 0 이하인 경우. <code>bytes</code>
	 *             array의 길이가 <code>booleansLength</code>의 <code>boolean</code>을
	 *             담을 만큼 크지 않은 경우. <code>align</code>이 <code>null</code>인 경우.
	 */
	public static boolean[] getBoolean(byte[] bytes, int booleansLength,
			Align align) {
		Assert.notNull(bytes, "bytes must not be null.");
		Assert.isTrue(booleansLength > 0,
				"booleansLength must be a positive integer.");
		Assert.isTrue(bytes.length * 8 >= booleansLength,
				"bytes is not sufficient for booleans.");

		boolean[] booleans = new boolean[booleansLength];
		int booleanPosition = 0;

		switch (align) {
		case Left:
			for (byte b : bytes) {
				int mask = 0x00000080;
				for (int j = 0; j < 8; j++) {
					booleans[booleanPosition] = ((b & mask) != 0);
					booleanPosition++;
					if (booleanPosition == booleansLength) {
						return booleans;
					}
					mask = (mask >> 1);
				}
			}
			break;
		case Right:
			int mask = (0x00000080 >> (8 - (booleansLength % 8)));
			for (byte b : bytes) {
				while (mask != 0) {
					booleans[booleanPosition] = ((b & mask) != 0);
					booleanPosition++;
					mask = (mask >> 1);
				}
				mask = 0x00000080;
			}
			Assert.isTrue(booleanPosition == booleansLength, "Malfunctioned");
			return booleans;
		default:
			Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		}
		Assert.isTrue(false, "MUST NOT BE HAPPEND.");
		return null;
	}

	/**
	 * <code>byte</code> array를 <code>short</code>으로 변환한다.
	 * 
	 * @param bytes
	 *            <code>byte</code> array.
	 * @return <code>short</code> 값.
	 * @throws IllegalArgumentException
	 *             <code>bytes</code>가 <code>null</code>이거나 길이가 2가 아닌 경우.
	 */
	public static short getShort(byte[] bytes) {
		Assert.notNull(bytes, "bytes must not be null.");
		Assert.isTrue(bytes.length == 2, "bytes' length must be 2.");

		return (short) (((bytes[0] << 8) & 0xff00) | (bytes[1] & 0x00ff));
	}

	/**
	 * <code>byte</code> array를 <code>int</code>으로 변환한다.
	 * 
	 * @param bytes
	 *            <code>byte</code> array.
	 * @return <code>int</code> 값.
	 * @throws IllegalArgumentException
	 *             <code>bytes</code>가 <code>null</code>이거나 길이가 4가 아닌 경우.
	 */
	public static int getInt(byte[] bytes) {
		Assert.notNull(bytes, "bytes must not be null.");
		Assert.isTrue(bytes.length == 4, "bytes' length must be 4.");

		return ((bytes[0] << 24) & 0xff000000)
				| ((bytes[1] << 16) & 0x00ff0000)
				| ((bytes[2] << 8) & 0x0000ff00) | (bytes[3] & 0x000000ff);
	}

	/**
	 * <code>byte</code> array를 <code>long</code>으로 변환한다.
	 * 
	 * @param bytes
	 *            <code>byte</code> array.
	 * @return <code>long</code> 값.
	 * @throws IllegalArgumentException
	 *             <code>bytes</code>가 <code>null</code>이거나 길이가 8가 아닌 경우.
	 */
	public static long getLong(byte[] bytes) {
		Assert.notNull(bytes, "bytes must not be null.");
		Assert.isTrue(bytes.length == 8, "bytes' length must be 8.");

		int high = ((bytes[0] << 24) & 0xff000000)
				| ((bytes[1] << 16) & 0x00ff0000)
				| ((bytes[2] << 8) & 0x0000ff00) | (bytes[3] & 0x000000ff);
		int low = ((bytes[4] << 24) & 0xff000000)
				| ((bytes[5] << 16) & 0x00ff0000)
				| ((bytes[6] << 8) & 0x0000ff00) | (bytes[7] & 0x000000ff);
		return (long) ((((long) high) << 32) | low);
	}

	/** 기본 Buffer Size */
	public static final int DEFAULT_READ_BYTES_BUFFER_SIZE = 2048;

	/**
	 * {@link InputStream}에서 <code>byte</code> array를 읽는다.
	 * <p>
	 * Buffer 크기는 {@link #DEFAULT_READ_BYTES_BUFFER_SIZE}를 사용한다.
	 * 
	 * @param inputStream
	 *            {@link InputStream}.
	 * @return 읽은 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 */
	public static byte[] readBytes(InputStream inputStream) throws IOException {
		return readBytes(inputStream, DEFAULT_READ_BYTES_BUFFER_SIZE);
	}

	/**
	 * {@link InputStream}에서 <code>byte</code> array를 읽는다.
	 * 
	 * @param inputStream
	 *            {@link InputStream}.
	 * @param bufferSize
	 *            Buffer 크기.
	 * @return 읽은 <code>byte</code> array.
	 * @throws IOException
	 *             {@link InputStream}에서 <code>byte</code> array를 읽는 과정에서 오류가
	 *             발생한 경우.
	 */
	public static byte[] readBytes(InputStream inputStream, int bufferSize)
			throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        
        int bcount = 0, n=0;
//        byte[] buf = new byte[bufferSize];
        byte[] buf = new byte[bufferSize];
        byte[] byteTemp = null;
        
        int read_retry_count = 0;
        do {
            n = inputStream.read(buf);
        	
//        	if(n > 0){
//        		if(n < bufferSize){
//        			byteTemp = new byte[n];
//        			System.arraycopy(buf, 0, byteTemp, 0, n);
//        			bout.write(byteTemp);
//        		} else {
//        			bout.write(buf);
//        		}
//        	}
//        	else if(n == -1)
//        		break;
//        	else if(n == 0 && ++read_retry_count == MAX_RETRY_COUNT)
//        		throw new IOException("read-retry-count(" + read_retry_count + ") exceed!");
        	
            if(n > 0) {
                bcount += n;
                bout.write(buf, 0, n);
            }
            else if(n == -1)
            	break;
            else if(n == 0 && ++read_retry_count == MAX_RETRY_COUNT)
                throw new IOException("read-retry-count(" + read_retry_count + ") exceed!");
        } while(true);
        bout.flush();

        return bout.toByteArray();
	}

	public static void writeBytes(OutputStream out, byte[] bytes) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(out);
		bos.write(bytes);
		bos.flush();
	}

	public static boolean isNullBytes(byte[] bytes, byte nullByte) {
		if (bytes == null) {
			return true;
		}
		for (byte b : bytes) {
			if (b != nullByte) {
				return false;
			}
		}
		return true;
	}

	public static int readInt(int length, InputStream inputStream,
			Charset charset) throws IOException {
		return readInt(length, Align.Right, ZERO, inputStream, charset);
	}

	public static int readInt(int length, Align align, byte padding,
			InputStream inputStream, Charset charset) throws IOException {
		String intString = nullablePaddingRead(charset, length, align, padding,
				inputStream);
		if (intString == null) {
			return 0;
		} else {
			return Integer.parseInt(intString);
		}
	}

	public static void writeInt(int value, int length,
			OutputStream outputStream, Charset charset) throws IOException {
		writeInt(value, length, Align.Right, ZERO, outputStream, charset);
	}

	public static void writeInt(int value, int length, Align align,
			byte padding, OutputStream outputStream, Charset charset)
			throws IOException {
		nullablePaddingWrite(String.valueOf(value), charset, length, align,
				padding, outputStream);
	}

	public static long readLong(int length, InputStream inputStream,
			Charset charset) throws IOException {
		return readLong(length, Align.Right, ZERO, inputStream, charset);
	}

	public static long readLong(int length, Align align, byte padding,
			InputStream inputStream, Charset charset) throws IOException {
		String intString = nullablePaddingRead(charset, length, align, padding,
				inputStream);
		if (intString == null) {
			return 0;
		} else {
			return Long.parseLong(intString);
		}
	}

	public static void writeLong(long value, int length,
			OutputStream outputStream, Charset charset) throws IOException {
		writeLong(value, length, Align.Right, ZERO, outputStream, charset);
	}

	public static void writeLong(long value, int length, Align align,
			byte padding, OutputStream outputStream, Charset charset)
			throws IOException {
		nullablePaddingWrite(String.valueOf(value), charset, length, align,
				padding, outputStream);
	}

	public static String readString(int length, InputStream inputStream,
			Charset charset) throws IOException {
		return nullablePaddingRead(charset, length, Align.Left, SPACE,
				inputStream);
	}

	public static void writeString(String value, int length,
			OutputStream outputStream, Charset charset) throws IOException {
		nullablePaddingWrite(value, charset, length, Align.Left, SPACE,
				outputStream);
	}

	public static void writeString(String value, int length,
			OutputStream outputStream, Charset charset, boolean trucate)
			throws IOException {
		if (value == null) {
			writeString(value, length, outputStream, charset);
		} else {
			byte[] bytes = value.getBytes(charset);
			if (length < bytes.length) {
				byte[] newBytes = new byte[length];
				System.arraycopy(bytes, 0, newBytes, 0, length);
				bytes = newBytes;
			}
			outputStream.write(bytes);
			for (int i = bytes.length; i < length; i++) {
				outputStream.write(SPACE);
			}
		}
	}

	public static String readFixedLengthString(int length,
			InputStream inputStream, Charset charset) throws IOException {
		return new String(read(length, inputStream), charset);
	}

	public static void writeFixedLengthString(String value, int length,
			OutputStream outputStream, Charset charset) throws IOException {
		Assert.notNull(value);
		byte[] bytes = value.getBytes(charset);
		Assert.isTrue(bytes.length == length);
		outputStream.write(bytes);
	}

	private static final int YEAR_SIZE = 4;

	private static final int MONTH_SIZE = 2;

	private static final int DATE_SIZE = 2;

	private static final int HOUR_OF_DAY_SIZE = 2;

	private static final int MINUTE_SIZE = 2;

	private static final int SECOND_SIZE = 2;

	private static final int MILLISECOND_SIZE = 3;

	private static final int CALENDAR_SIZE = YEAR_SIZE + MONTH_SIZE + DATE_SIZE
			+ HOUR_OF_DAY_SIZE + MINUTE_SIZE + SECOND_SIZE + MILLISECOND_SIZE;

	public static Calendar readCalendar(InputStream inputStream, Charset charset)
			throws IOException {
		byte[] bytes = ByteUtil.read(CALENDAR_SIZE, inputStream);
		if (isNullBytes(bytes, SPACE)) {
			return null;
		}

		int offset = 0;
		int year = getDecimal(bytes, offset, YEAR_SIZE);
		offset += YEAR_SIZE;
		int month = getDecimal(bytes, offset, MONTH_SIZE);
		offset += MONTH_SIZE;
		int date = getDecimal(bytes, offset, DATE_SIZE);
		offset += DATE_SIZE;
		int hourOfDay = getDecimal(bytes, offset, HOUR_OF_DAY_SIZE);
		offset += HOUR_OF_DAY_SIZE;
		int minute = getDecimal(bytes, offset, MINUTE_SIZE);
		offset += MINUTE_SIZE;
		int second = getDecimal(bytes, offset, SECOND_SIZE);
		offset += SECOND_SIZE;
		int millisecond = getDecimal(bytes, offset, MILLISECOND_SIZE);
		// offset += MILLISECOND_SIZE;

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hourOfDay, minute, second);
		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar;
	}

	public static void writeCalendar(Calendar calendar,
			OutputStream outputStream, Charset charset) throws IOException {
		if (calendar == null) {
			nullablePaddingWrite(null, charset, CALENDAR_SIZE, Align.Left,
					SPACE, outputStream);
		} else {
			writeInt(calendar.get(Calendar.YEAR), YEAR_SIZE, Align.Right, ZERO,
					outputStream, charset);
			writeInt(calendar.get(Calendar.MONTH) + 1, MONTH_SIZE, Align.Right,
					ZERO, outputStream, charset);
			writeInt(calendar.get(Calendar.DATE), DATE_SIZE, Align.Right, ZERO,
					outputStream, charset);
			writeInt(calendar.get(Calendar.HOUR_OF_DAY), HOUR_OF_DAY_SIZE,
					Align.Right, ZERO, outputStream, charset);
			writeInt(calendar.get(Calendar.MINUTE), MINUTE_SIZE, Align.Right,
					ZERO, outputStream, charset);
			writeInt(calendar.get(Calendar.SECOND), SECOND_SIZE, Align.Right,
					ZERO, outputStream, charset);
			writeInt(calendar.get(Calendar.MILLISECOND), MILLISECOND_SIZE,
					Align.Right, ZERO, outputStream, charset);
		}
	}

	public static int getDecimal(byte[] bytes, int offset, int length) {
		Assert.notNull(bytes);
		Assert.isTrue(0 <= offset,
				"offset must be equal to or greater than zero : offset = "
						+ offset);
		Assert.isTrue(0 <= length,
				"length must be equal to or greater than zero : length = "
						+ length);
		Assert.isTrue(offset + length <= bytes.length,
				"length of bytes must be equal to or greate than offset + length : offset = "
						+ offset + ", length = " + length);
		int value = 0;
		for (int i = offset; i < offset + length; i++) {
			byte b = bytes[i];
			Assert.isTrue(ZERO <= b && b <= NINE,
					"byte is not a decimal character : '" + b + "'");
			value = value * 10 + (b - ZERO);
		}

		return value;
	}

	public static int byteArrayToInt(byte[] bytes) {

	    final int size = Integer.SIZE / 8;
	    ByteBuffer buff = ByteBuffer.allocate(size);
	    final byte[] newBytes = new byte[size];
	    for (int i = 0; i < size; i++) {
	        if (i + bytes.length < size) {
	            newBytes[i] = (byte) 0x00;
	        } else {
	            newBytes[i] = bytes[i + bytes.length - size];
	        }
	    }
	    buff = ByteBuffer.wrap(newBytes);
	    buff.order(ByteOrder.BIG_ENDIAN);
	    return buff.getInt();
	}
}