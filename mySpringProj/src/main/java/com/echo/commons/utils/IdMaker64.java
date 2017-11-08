package com.echo.commons.utils;

import com.echo.commons.service.IDGenService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 64位Id生成器
 *
 * @author Echo-cxt
 * @create 2017-11-08 5:55 PM
 **/
public class IdMaker64 implements IDGenService{

    private int counter = 0;

    private Random r;

    private char[] initId = {'0'};

    private static int rand_limit = 16777216;

    private static int count_limit = 262144;

    private int ipJump = 1;

    private static char[] datas = {'0','1','2','3','4','5','6','7','8','9','A','a','B','b','C','c','D','d','E',
            'e','F','f','G','g','H','h','I','i','J','j','K','k','L','l','M','m','N','n','O','o','P','p','Q','q',
            'R','r','S','s','T','t','U','u','V','v','W','X','x','Y','y','Z','z'};

    private static long mask = 63L;

    private Object _syn = new Object();

    public IdMaker64(){
        int ipInt = 0;
        InetAddress addr = null;
        try{
            addr = Inet4Address.getLocalHost();
            ipInt = addr.hashCode();
            this.ipJump = (Math.abs(ipInt % 64) + 1);
        }catch (UnknownHostException e){}
        long seed = System.currentTimeMillis() + ipInt;

        this.r = new Random(seed);
        this.initId[0] = datas[(this.r.nextInt(22) + 10)];
    }

    private String make(){
        return _make();
    }

    private String _make(){
        char[] str = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

        int count = getCount();
        long time = System.currentTimeMillis();
        int charPos = 8;
        do{
            str[(charPos--)] = datas[((int)(time & mask))];
            time >>>= 6;
        }while(time != 0L);

        charPos = str.length - 5;
        do{
            str[(charPos--)] = datas[((int)(count & mask))];
            count >>>= 6;
        }while (count != 0);

        str[0] = this.initId[0];
        int r_nums = this.r.nextInt(rand_limit);
        charPos = str.length - 1;
        do{
            str[(charPos--)] = datas[((int)(r_nums & mask))];
            r_nums >>>= 6;
        }while(r_nums != 0);
        return new String(str);
    }

    private synchronized int getCount(){
        synchronized (this._syn){
            this.counter += this.ipJump;
            if((this.counter < 0) || (this.counter > count_limit)){
                this.counter = this.ipJump;

                try{
                    this._syn.wait(16L);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return this.counter;
        }
    }

    @Override
    public String getID() {
        return make();
    }
}
