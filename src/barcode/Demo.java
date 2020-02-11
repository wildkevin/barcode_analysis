package barcode;

public class Demo{
	
	static boolean nonIntCheck(char c) {
		if (!(48 <= c && 57 >= c))
			return true;
		return false;
	}
	
	static int nonIntCount(String test) {
		int count = 0;
		
		for (int i = 0; i < test.length(); i++)
		{
			if (nonIntCheck(test.charAt(i)))
				count++;
		}
		return count;
	}
	
	static int charString(String test) {
		
		for (int i=0; i < test.length();i++) {
			if (nonIntCheck(test.charAt(i)))
				return i;
		}
		return -1;
	}
	
	static int[] nonIntPos(String test) {
		
		int index = -1;
		int n = -1;
		int lcount = nonIntCount(test);
		int[] charpos = new int[lcount];
		
		for (int i = 0; i < lcount; i++)
		{
			n = charString(test.substring(n+1));
			charpos[i] = index + n + 1;
			index = charpos[i];
			n = index;
		}
		return charpos;
	}
	
	static String barcode(final String code) {
		
		StringBuffer temp = new StringBuffer(code);
		
		final int[] lposit = nonIntPos(code);
		
		final int lcount = nonIntCount(code);
		
		if (charString(code) == -1) {
			if (code.length() % 2 == 0)
				temp.insert(0, ">;");
			else
			{
				temp.insert(0, ">:");
				temp.insert(3, ">5");
			}
			return temp.toString();
		}
		
		// j is # of code change
		int j = 0;
		
		// 开头 4+ 连续数字 用code C
		if (lposit[0] >=4)
		{
			temp.insert(0, ">;");
			if (lposit[0] % 2 == 0)
				temp.insert(lposit[0]+2, ">6");
			else
				temp.insert(lposit[0]+1, ">6");
			j = 2;
		}
		else
		{
			temp.insert(0, ">:");
			j = 1;
		}
		
		// 中间 6+ 连续数字 用code C
		int midDiff = 0;
		for (int i = 0; i < lcount-1; i++)
		{
			midDiff = lposit[i+1] - lposit[i];
			if (midDiff >= 7)
			{
				if (midDiff % 2 == 1)
				{
					temp.insert(lposit[i] + 2 * j + 1, ">5");
					j++;
					temp.insert(lposit[i] + 2 * j + midDiff, ">6");
					j++;
				}
				else
				{
					temp.insert(lposit[i] + 2 * j + 2, ">5");
					j++;
					temp.insert(lposit[i] + 2 * j + midDiff, ">6");
					j++;
				}
			}
		}
		
		// 结尾 4+ 连续数字 用code C
		int endDiff = code.length() - lposit[lcount-1] - 1;
		
		if (endDiff >=4 )
		{
			if (endDiff % 2 == 0)
				temp.insert((temp.length() - endDiff), ">5");
			else
				temp.insert((temp.length() - endDiff + 1), ">5");
		}
		
		
		return temp.toString();
	}
	
	public static void main(String args[]) {

		String a = "098x1234567y23";

		System.out.println(barcode(a));

	}
}





