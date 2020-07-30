package com.yunlong.softpark.util;

/**
 * @Author 王海澎
 * @Date 2020/7/30 9:33
 * @Version 1.0
 */

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成图形验证码的工具类
 */
@Component
public class ImageCodeUtil {
    static Random random = new Random();
    int width = 150;
    int height = 75;
    int fontsize = 30;

    String str = "0123456789abcdefghigklmnopqrstuvwxyz";

    //创建用于随机生成验证码的的字符串
    String randCode() {
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += str.charAt(random.nextInt(str.length()));
        }
        return code;
    }

    //该方法用于随机生成4个字符保存在code中，并返回
    Color randColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    //设置使图片扭曲的方法
    static void shear(Graphics g, int width, int height, Color color) {
        shearX(g, width, height, color);
        shearY(g, width, height, color);
    }

    //向y轴旋转
    static void shearY(Graphics g, int width, int height, Color color) {
        int period = random.nextInt(40) + 10;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < width; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, height, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + height, i, height);
            }
        }
    }

    //向X轴旋转
    static void shearX(Graphics g, int width, int height, Color color) {
        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < height; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, width, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + width, i, width, i);
            }
        }
    }
    public BufferedImage getCodeImage(HttpServletRequest request, HttpServletResponse response){
        //设置画板（图片）参数（宽度，高度，图片颜色）
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D pen = (Graphics2D) img.getGraphics();
        //设置边框颜色
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, width, height);
        //获取随机四个字符
        String code = randCode();
        //将生成的字符保存在session中
        request.getSession().setAttribute("ImageCode", code);
        //设置绘制区域
        pen.fillRect(0, 0, width, height);
        //设置字体(字体，加粗，大小）
        pen.setFont(new Font("微软雅黑", Font.BOLD, fontsize));
        //依次为code中的字符上色
        for (int i = 0; i < code.length(); i++) {
            pen.setColor(randColor());
            //画笔随机染色
            pen.drawString(code.charAt(i) + "", 5 + i * fontsize, (fontsize + height) / 2);
            // 用生成的画笔颜色在画板上书写字符，后两个参数代表在画板上书写的位置
        }
        //绘制干扰线，增加识别难度
        for (int i = 0; i < 2; i++) {
            pen.setColor(randColor());
            pen.setStroke(new BasicStroke(2));
            // 设置画笔粗细
            pen.drawLine(random.nextInt(width / 2), random.nextInt(height), random.nextInt(width), random.nextInt(height));
            // 绘制干扰线，参数代表干扰线绘制位置
        }

        //添加噪点
        float yawRate = 0.05f;
        //噪声率
        int area = (int) (yawRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            pen.setColor(randColor());
            img.setRGB(x, y, random.nextInt());
        }

        return img;
    }
}
