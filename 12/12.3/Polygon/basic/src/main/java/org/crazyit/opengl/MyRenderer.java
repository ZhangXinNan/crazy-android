package org.crazyit.opengl;

import android.opengl.GLSurfaceView;

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
public class MyRenderer implements GLSurfaceView.Renderer
{
	float[] triangleData = new float[] {
			0.1f, 0.6f , 0.0f , // 上顶点
			-0.3f, 0.0f , 0.0f , // 左顶点
			0.3f, 0.1f , 0.0f  // 右顶点
	};
	int[] triangleColor = new int[] {
			65535, 0, 0, 0, // 上顶点红色
			0, 65535, 0, 0, // 左顶点绿色
			0, 0, 65535, 0 // 右顶点蓝色
	};
	float[] rectData = new float[] {
			0.4f, 0.4f , 0.0f, // 右上顶点
			0.4f, -0.4f , 0.0f, // 右下顶点
			-0.4f, 0.4f , 0.0f, // 左上顶点
			-0.4f, -0.4f , 0.0f // 左下顶点
	};
	int[] rectColor = new int[] {
			0, 65535, 0, 0, // 右上顶点绿色
			0, 0, 65535, 0, // 右下顶点蓝色
			65535, 0, 0, 0, // 左上顶点红色
			65535, 65535, 0, 0 // 左下顶点黄色
	};
	// 依然是正方形的4个顶点，只是顺序交换一下
	float[] rectData2 = new float[] {
			-0.4f, 0.4f , 0.0f, // 左上顶点
			0.4f, 0.4f , 0.0f, // 右上顶点
			0.4f, -0.4f , 0.0f, // 右下顶点
			-0.4f, -0.4f , 0.0f // 左下顶点
	};
	float[] pentacle = new float[]{
			0.4f , 0.4f , 0.0f,
			-0.2f , 0.3f , 0.0f,
			0.5f , 0.0f , 0f,
			-0.4f , 0.0f , 0f,
			-0.1f, -0.3f , 0f
	};
	FloatBuffer triangleDataBuffer;
	IntBuffer triangleColorBuffer;
	FloatBuffer rectDataBuffer;
	IntBuffer rectColorBuffer;
	FloatBuffer rectDataBuffer2;
	FloatBuffer pentacleBuffer;
	public MyRenderer()
	{
		// 将顶点位置数据数组转换成FloatBuffer
		triangleDataBuffer = floatBufferUtil(triangleData);
		rectDataBuffer = floatBufferUtil(rectData);
		rectDataBuffer2 = floatBufferUtil(rectData2);
		pentacleBuffer = floatBufferUtil(pentacle);
		// 将顶点颜色数据数组转换成IntBuffer
		triangleColorBuffer = intBufferUtil(triangleColor);
		rectColorBuffer = intBufferUtil(rectColor);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT
				, GL10.GL_FASTEST);
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
		// 调用此方法设置透视视窗的空间大小
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
		// 设置当前矩阵堆栈为模型堆栈
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// --------------------绘制第一个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(-0.32f, 0.35f, -1.2f);  // ①
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
		// 设置顶点的颜色数据
		gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		// --------------------绘制第二个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(0.6f, 0.8f, -1.5f);
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
		// 设置顶点的颜色数据
		gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		// --------------------绘制第三个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(-0.4f, -0.5f, -1.5f);
		// 设置顶点的位置数据（依然使用之前的顶点颜色）
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer2);
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		// --------------------绘制第四个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(0.4f, -0.5f, -1.5f);
		// 设置使用纯色填充
		gl.glColor4f(1.0f, 0.2f, 0.2f, 0.0f);  // ②
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pentacleBuffer);
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 5);
		// 绘制结束
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	// 定义一个工具方法，将int[]数组转换为OpenGL ES所需的IntBuffer
	private IntBuffer intBufferUtil(int[] arr)
	{
		IntBuffer mBuffer;
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4字节
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
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asFloatBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}
}
