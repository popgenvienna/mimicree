package mimicree.data.haplotypes;

/** 
 * Immutable representation of a bitarray;
 * Can for example be used to represent haplotypes
 * 
 * May be constructed using two types of BitArrayBuilder
 * @author robertkofler
 *
 */
public class BitArray 
{
	// The following byte-sequence allows to address every bit separately (out of 8 bits)
	private static byte[] bitdec = {1 , 2 , 4 , 8 , 16 , 32 , 64 , -128};
	
	/*
	public static class BitArrayBuilderAdditive
	{
		private byte[] bitar;
		private int size;
		private int runningPositionCount=0;
		
		public BitArrayBuilderAdditive(int size)
		{
			if(!(size>0)) throw new IllegalArgumentException("Size of BitArrayBuilderAdditive needs to be larger than zero");
			this.size=size;
			int realsize=(int)(size/8.0);
			int modulo=size%8;
			if(modulo>0) realsize++;
			bitar =new byte[realsize];
		}
		
		/** 
		 * Add a bit to the builder
		 * @param toAdd
		 
		public void add(boolean toAdd)
		{
			if(runningPositionCount >=size) throw new IndexOutOfBoundsException("Can not add element to BitArrayBuilderAdditive; Reached maximum size "+size);
			
			int real=(int)(runningPositionCount/8.0);
			int mod=runningPositionCount % 8;
			
			if(toAdd) this.bitar[real] |= BitArray.bitdec[mod]; 

			runningPositionCount++;
		}
		
		/**
		 * Obtain a immutable instance of the BitArray 
		 * @return
		 
		public BitArray getBitArray()
		{
			return new BitArray(this.bitar,this.size);
		}
		
	}
	*/
	
	/**
	 * Build a new BitArray; 
	 * Mutable brother of the immutable BitArray
	 * @author robertkofler
	 *
	 */
	public static class BitArrayBuilder
	{
		private byte[] bitar;
		private int size;
		public BitArrayBuilder(int size)
		{
	
			if(!(size>0)) throw new IllegalArgumentException("Size of BitArray needs to be larger than zero");
			this.size=size;
			
			int realsize=(int)(size/8.0);
			int modulo=size%8;
			if(modulo>0) realsize++;
			bitar =new byte[realsize];
		}

		/**
		 * Set the bit at a given position
		 * @param position
		 */
		public void setBit(int position)
		{
			if(position >=size ) throw new IndexOutOfBoundsException("Index in BitArrayBuilder is out of range");
			
			// Calculate the components
			int real=(int)(position/8.0);
			int mod=position % 8;
			
			// Set the corresponding bit; Only when value is true
			this.bitar[real] |= BitArray.bitdec[mod]; 
			
		}
		
		/**
		 * Obtain a immutable instance of the BitArray 
		 * @return
		 */
		public BitArray getBitArray()
		{
			return new BitArray(this.bitar,this.size);
		}
		
	}
	
	private final byte[] bitar;
	private final int size;
	
	public BitArray(byte[] bitar, int size)
	{
		// Ensure immutability by cloning
		this.bitar=bitar.clone();
		this.size=size;
	}
	
	
	/**
	 * Is the bit at the given position set?
	 * @param position
	 * @return
	 */
	public boolean isSet(int position)
	{
		if(position >=size ) throw new IndexOutOfBoundsException("Index in BitArray is out of range");
		// Calculate the components
		int real=(int)(position/8.0);
		int mod=position % 8;
		
		// Calculate the result of the logical and to see if the bit at the position is set
		int res= this.bitar[real] & bitdec[mod];
		
		if(res>0) return true;
		return false;
	}

}
