package org.noear.simplemq.server;

/**
 * @author Afish
 * @date 2025/3/12 13:47
 * @since 1.0
 */
public class MqNextTime {
    public static boolean allowDistribute(MqMessageHolder messageHolder){
        if(messageHolder.getNextTime() <= System.currentTimeMillis()){
            return true;
        }else {
            return false;
        }
    }

    public static long getNextTime(MqMessageHolder messageHolder) {
        switch (messageHolder.getTimes()){
            case 0:
                return 0;
            case 1:
                return System.currentTimeMillis() + 1000 * 5;
            case 2:
                return System.currentTimeMillis() + 1000 * 30;
            case 3:
                return System.currentTimeMillis() + 1000 * 60 * 3;
            case 4:
                return System.currentTimeMillis() + 1000 * 60 * 9;
            case 5:
                return System.currentTimeMillis() + 1000 * 60 * 15;
            case 6:
                return System.currentTimeMillis() + 1000 * 60 * 30;
            case 7:
                return System.currentTimeMillis() + 1000 * 60 * 60;
            default:
                return System.currentTimeMillis() + 1000 * 60 * 60 * 2;
        }
    }
}
