package com.bao.miaosha.test.imageVericode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestImage {

    public static String generateCode(int code){
        String str = "ADberjrewoqnronsfddsafnoin2348239401234n2134u90ndsajf";
        char[] chars = str.toCharArray();
        char[] codeChars = new char[code];

        for (int i = 0; i < code; i++) {
            codeChars[i] = chars[TestImage.rand(str.length())];
        }

        return new String(codeChars);
    }

    public static void main(String[] args) throws IOException {
        // 生成验证码
        String code = TestImage.generateCode(6);

        // 生成图片
        BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 200, 50);

        for (int i = 0; i < 100; i++) {
            graphics.setColor(new Color(TestImage.rand(0xffffff)));
            graphics.drawArc(TestImage.rand(200), TestImage.rand(50), TestImage.rand(200), TestImage.rand(50), rand(360), rand(360));
        }

        // 验证吗剧中处理
        Font font = new Font("黑体", Font.BOLD, 20);
        TextLayout textLayout = new TextLayout(code, font, graphics.getFontRenderContext());
        Rectangle2D rectangle2D = textLayout.getBounds();
        int x = (int) ((200 - rectangle2D.getWidth())/2);
        int y = (int) ((50 - rectangle2D.getHeight())/2);

        graphics.setColor(Color.red);
        graphics.setFont(font);
        graphics.drawString(code, (int) (x - rectangle2D.getX()), (int) (y - rectangle2D.getY()));

        // 把图片写到文件中去
        ImageIO.write(image, "png", new File("a.png"));
    }

    public static int rand(int a){
        return (int) (Math.random() * a);
    }
}
