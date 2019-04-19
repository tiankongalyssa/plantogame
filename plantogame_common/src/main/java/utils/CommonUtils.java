package utils;

public class CommonUtils
{
    public static int getTotal(int[] award)
    {
        int result = 0;
        for (int anAward : award) {
            result += anAward;
        }
        return result;
    }

    public static int[] getIntOfAward(String data)
    {
        String[] split = data.split(",");

        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (s.contains("@")) {
                split[i] = s.substring(0, 1);
                break;
            }
        }
        int[] result = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Integer.valueOf(split[i]).intValue();
        }
        return result;
    }
}