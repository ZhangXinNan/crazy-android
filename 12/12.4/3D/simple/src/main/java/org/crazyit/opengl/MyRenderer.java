package org.crazyit.opengl;

import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MyRenderer implements Renderer
{
	// 定义三棱椎的4个顶点
	float[] taperVertices = new float[] {
			0.0f, 0.5f, 0.0f,
			-0.5f, -0.5f, -0.2f,
			0.5f, -0.5f, -0.2f,
			0.0f, -0.2f, 0.2f
	};
	// 定义三棱椎的4个顶点的颜色
	int[] taperColors = new int[]{
			65535, 0, 0, 0,  // 红色
			0, 65535, 0, 0,	 // 绿色
			0, 0, 65535, 0,  // 蓝色
			65535, 65535, 0, 0 //黄色
	};
	// 定义三棱椎的4个三角面
	private byte[] taperFacets = new byte[]{
			0, 1, 2, // 0、1、2三个顶点组成一个面
			0, 1, 3, // 0、1、3三个顶点组成一个面
			1, 2, 3, // 1、2、3三个顶点组成一个面
			0, 2, 3 // 0、2、3三个顶点组成一个面
	};
	// 定义立方体的8个顶点
	float[] cubeVertices = new float[] {
			// 上顶面正方形的4个顶点
			0.5f, 0.5f, 0.5f,
			0.5f, -0.5f, 0.5f,
			-0.5f, -0.5f, 0.5f,
			-0.5f, 0.5f, 0.5f,
			// 下底面正方形的4个顶点
			0.5f, 0.5f, -0.5f,
			0.5f, -0.5f, -0.5f,
			-0.5f, -0.5f, -0.5f,
			-0.5f, 0.5f, -0.5f
	};
	// 定义立方体所需要的6个面（一共是12个三角形所需的顶点）
	private byte[] cubeFacets = new byte[]{
			0, 1, 2,
			0, 2, 3,
			2, 3, 7,
			2, 6, 7,
			0, 3, 7,
			0, 4, 7,
			4, 5, 6,
			4, 6, 7,
			0, 1, 4,
			1, 4, 5,
			1, 2, 6,
			1, 5, 6
	};
	// 定义Open GL ES绘制所需要的Buffer对象
	FloatBuffer taperVerticesBuffer;
	IntBuffer taperColorsBuffer;
	ByteBuffer taperFacetsBuffer;
	FloatBuffer cubeVerticesBuffer;
	ByteBuffer cubeFacetsBuffer;
	// 控制旋转的角度
	private float rotate;
	public MyRenderer()
	{
		// 将三棱椎的顶点位置数据数组包装成FloatBuffer
		taperVerticesBuffer = floatBufferUtil(taperVertices);
		// 将三棱椎的4个面的数组包装成ByteBuffer
		taperFacetsBuffer = ByteBuffer.wrap(taperFacets);
		// 将三棱椎的4个定点的颜色数组包装成IntBuffer
		taperColorsBuffer = intBufferUtil(taperColors);
		// 将立方体的顶点位置数据数组包装成FloatBuffer
		cubeVerticesBuffer = floatBufferUtil(cubeVertices);
		// 将立方体的6个面（12个三角形）的数组包装成ByteBuffer
		cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// 设置阴影平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// 设置3D视窗的大小及位置
		gl.glViewport(0, 0, width, height);
		// 将当前矩阵模式设为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 初始化单位矩阵
		gl.glLoadIdentity();
		// 计算透视视窗的宽度、高度比
		float ratio = (float) width / height;
		// 调用此方法设置透视视窗的空间大小。
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}
	// 绘制图形的方法
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕缓存和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 启用顶点坐标数据
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 启用顶点颜色数据
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 设置当前矩阵模式为模型视图。
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// --------------------绘制第一个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(-0.6f, 0.0f, -1.5f);
		// 沿着Y轴旋转
		gl.glRotatef(rotate, 0f, 0.2f, 0f);
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, taperVerticesBuffer);
		// 设置顶点的颜色数据
		gl.glColorPointer(4, GL10.GL_FIXED, 0, taperColorsBuffer);
		// 按taperFacetsBuffer指定的面绘制三角形
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP
				, taperFacetsBuffer.remaining(),
				GL10.GL_UNSIGNED_BYTE, taperFacetsBuffer);
		// --------------------绘制第二个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(0.7f, 0.0f, -2.2f);
		// 沿着Y轴旋转
		gl.glRotatef(rotate, 0f, 0.2f, 0f);
		// 沿着X轴旋转
		gl.glRotatef(rotate, 1f, 0f, 0f);
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVerticesBuffer);
		// 不设置顶点的颜色数据，还用以前的颜色数据
		// 按cubeFacetsBuffer指定的面绘制三角形
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP
				, cubeFacetsBuffer.remaining(),
				GL10.GL_UNSIGNED_BYTE, cubeFacetsBuffer);
		// 绘制结束
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// 旋转角度增加1
		rotate+=1;
	}
	// 定义一个工具方法，将int[]数组转换为OpenGL ES所需的IntBuffer
	private IntBuffer intBufferUtil(int[] arr)
	{
		IntBuffer mBuffer;
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}
	// 定义一个工具方法，将float[]数组转换为OpenGL ES所需的FloatBuffer
	private FloatBuffer floatBufferUtil(float[] arr)
	{
		FloatBuffer mBuffer;
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asFloatBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}
}