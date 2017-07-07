package com.dels.utils;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by l13595 on 2017/4/19.
 * 验证码生成工具类
 */
public class VerificationCode {


    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");  //生成的响应是图片

        Font mFont = new Font("Times New Roman", Font.PLAIN, 21);
        int width=100, height=25;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200,250));
        g.fillRect(1, 1, width-1, height-1);
        g.setColor(new Color(102,102,102));
        g.drawRect(0, 0, width-1, height-1);
        g.setFont(mFont);

        g.setColor(getRandColor(160,200));

        //画随机线
        for (int i=0;i<155;i++){
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x,y,x + xl,y + yl);
        }

        //从另一方向画随机线
        for (int i = 0;i < 70;i++){
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x,y,x - xl,y - yl);
        }

        //生成随机数,字母加数字
        StringBuffer sRand=new StringBuffer();
        for (int i=0;i<4;i++){
            int selectTmp = random.nextInt(2);
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            if(selectTmp==0){ //随机数字
                int intTmp = random.nextInt(10);
                g.drawString(String.valueOf(intTmp),15*i+10,20);
                sRand.append(intTmp);
            }else{ //随机字母
                int itmp = random.nextInt(26) + 65;
                char chatTmp = (char)itmp;
                g.drawString(String.valueOf(chatTmp),15*i+10,20);
                sRand.append(chatTmp);
            }
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("randomCode",sRand.toString().toLowerCase());
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * 获取随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    /**
     * 生成动态口令
     * @param i：生成动态口令的位数
     * @return
     */
    public String oneTimePassword(int i){
        Random rd = new Random();
        StringBuffer bf = new StringBuffer();
        for(int j=0; j<i; j++){
            int x = rd.nextInt(10);
            bf.append(x);
        }
        return bf.toString();
    }

    public void getOneTimePdImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");  //生成的响应是图片
        int width=120, height=35;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200,250));
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }


}
