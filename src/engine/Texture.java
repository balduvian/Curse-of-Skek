package engine;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;

public class Texture {
	
	protected int id;
	
	public Texture(String path) {
		try {
			BufferedImage b = ImageIO.read(new File(path));
			int h = b.getHeight();
			int w = b.getWidth();
			int[] pixels = b.getRGB(0, 0, w, h, null, 0, w);
			ByteBuffer buffer = BufferUtils.createByteBuffer(w*h*4);
			for(int i = 0; i < h; ++i) {
				for(int j = 0; j < w; ++j) {
					int pixel = pixels[i*w+j];
					buffer.put((byte)((pixel >> 16) & 0xff));
					buffer.put((byte)((pixel >>  8) & 0xff));
					buffer.put((byte)((pixel      ) & 0xff));
					buffer.put((byte)((pixel >> 24) & 0xff));
				}
			}
			buffer.flip();
			id = glGenTextures();
			bind();
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			unbind();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void destroy() {
		glDeleteTextures(id);
	}
	
}
