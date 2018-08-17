package com.chujiu.security.captcha.engine;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.HorizontalSpaceGlyphsVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.RotateGlyphsRandomVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[Security]
 * Description: [验证码图片样式Engine，生成4位数字验证码]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class NumberEngine extends ListImageCaptchaEngine {

	@Override 
	protected void buildInitialFactories() {
		// word generator
		//WordGenerator words = new RandomWordGenerator("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
		WordGenerator words = new RandomWordGenerator("1234567890");
		TextPaster randomPaster = new GlyphsPaster(4, 4,
				//new SingleColorGenerator(new Color(0, 153, 255)),
				new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }),
				new GlyphsVisitors[] {
						new TranslateGlyphsVerticalRandomVisitor(1),
						new RotateGlyphsRandomVisitor(Math.PI / 32),
						new OverlapGlyphsUsingShapeVisitor(3),
						new HorizontalSpaceGlyphsVisitor(5),
						new TranslateAllToRandomPointVisitor(5, 5) });

		BackgroundGenerator back = new UniColorBackgroundGenerator(150, 55, new Color(238, 238, 238));
		FontGenerator shearedFont = new RandomFontGenerator(50, 50, new Font[] {
				new Font("Bell MT", Font.BOLD, 50),
				new Font("Credit valley", Font.BOLD, 50) 
				}, false);

		PinchFilter pinch1 = new PinchFilter();
		pinch1.setAmount(-.5f);
		pinch1.setRadius(70);
		pinch1.setAngle((float) (Math.PI / 16));
		pinch1.setCentreX(0.5f);
		pinch1.setCentreY(-0.01f);
		pinch1.setEdgeAction(ImageFunction2D.CLAMP);

		PinchFilter pinch2 = new PinchFilter();
		pinch2.setAmount(-.6f);
		pinch2.setRadius(70);
		pinch2.setAngle((float) (Math.PI / 16));
		pinch2.setCentreX(0.3f);
		pinch2.setCentreY(1.01f);
		pinch2.setEdgeAction(ImageFunction2D.CLAMP);

		PinchFilter pinch3 = new PinchFilter();
		pinch3.setAmount(-.6f);
		pinch3.setRadius(70);
		pinch3.setAngle((float) (Math.PI / 16));
		pinch3.setCentreX(0.8f);
		pinch3.setCentreY(-0.01f);
		pinch3.setEdgeAction(ImageFunction2D.CLAMP);

		List<ImageDeformation> textDef = new ArrayList<ImageDeformation>();
		textDef.add(new ImageDeformationByBufferedImageOp(pinch1));
		textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
		textDef.add(new ImageDeformationByBufferedImageOp(pinch3));

		// word2image
		WordToImage word2image = new DeformedComposedWordToImage(
				false, shearedFont, back, randomPaster, 
				new ArrayList<ImageDeformation>(),
				new ArrayList<ImageDeformation>(), textDef
		);

		this.addFactory(new GimpyFactory(words, word2image, false));
	}
}
