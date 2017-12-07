package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import scenes.GameScene;

abstract public class World {
	
	private int ID;
	
	protected EntityList entityList;
	
	private int width;
	private int height;
	
	private int[][] map;
	
	private ArrayList<Entity> keys;
	
	public static final int FLOOR = 0;
	public static final int TERRAIN = 1;
	public static final int BLOCK = 2;
	
	private static final int[] reference = {
			TERRAIN, //0
			FLOOR, //1
			FLOOR, //2
			FLOOR, //3
			FLOOR, //4
			FLOOR, //5
			FLOOR, //6
			FLOOR, //7
			FLOOR, //8
			FLOOR, //9
			FLOOR, //10
			FLOOR, //11
			FLOOR, //12
			FLOOR, //13
			FLOOR, //14
			FLOOR, //15
			FLOOR, //16
			FLOOR, //17
			FLOOR, //18
			FLOOR, //19
			FLOOR, //20
			FLOOR, //21
			FLOOR, //22
			FLOOR, //23
			FLOOR, //24
			FLOOR, //25
			FLOOR, //26
			FLOOR, //27
			FLOOR, //28
			FLOOR, //29
			FLOOR, //30
			FLOOR, //31
			FLOOR, //32
			FLOOR, //33
			FLOOR, //34
			FLOOR, //35
			FLOOR, //36
			FLOOR, //37
			FLOOR, //38
			FLOOR, //39
			FLOOR, //40
			FLOOR, //41
			FLOOR, //42
			FLOOR, //43
			FLOOR, //44
			FLOOR, //45
			FLOOR, //46
			FLOOR, //47
			FLOOR, //48
			FLOOR, //49
			FLOOR, //50
			FLOOR, //51
			FLOOR, //52
			FLOOR, //53
			FLOOR, //54
			FLOOR, //55
			FLOOR, //56
			FLOOR, //57
			FLOOR, //58
			FLOOR, //59
			FLOOR, //60
			FLOOR, //61
			FLOOR, //62
			FLOOR, //63
			FLOOR, //64
			FLOOR, //65
			FLOOR, //66
			FLOOR, //67
			FLOOR, //68
			FLOOR, //69
			FLOOR, //70
			FLOOR, //71
			FLOOR, //72
			FLOOR, //73
			FLOOR, //74
			FLOOR, //75
			FLOOR, //76
			FLOOR, //77
			FLOOR, //78
			FLOOR, //79
			FLOOR, //80
			FLOOR, //81
			FLOOR, //82
			FLOOR, //83
			FLOOR, //84
			FLOOR, //85
			FLOOR, //86
			FLOOR, //87
			FLOOR, //88
			FLOOR, //89
			FLOOR, //90
			FLOOR, //91
			FLOOR, //92
			FLOOR, //93
			FLOOR, //94
			FLOOR, //95
			FLOOR, //96
			FLOOR, //97
			FLOOR, //98
			FLOOR, //99
			BLOCK, //100
			FLOOR, //101
			FLOOR, //102
			FLOOR, //103
			FLOOR, //104
			FLOOR, //105
			FLOOR, //106
			FLOOR, //107
			FLOOR, //108
			FLOOR, //109
			FLOOR, //110
			FLOOR, //111
			FLOOR, //112
			FLOOR, //113
			FLOOR, //114
			FLOOR, //115
			FLOOR, //116
			FLOOR, //117
			FLOOR, //118
			FLOOR, //119
			FLOOR, //120
			FLOOR, //121
			FLOOR, //122
			FLOOR, //123
			FLOOR, //124
			FLOOR, //125
			FLOOR, //126
			FLOOR, //127
			FLOOR, //128
			FLOOR, //129
			FLOOR, //130
			FLOOR, //131
			FLOOR, //132
			FLOOR, //133
			FLOOR, //134
			FLOOR, //135
			FLOOR, //136
			FLOOR, //137
			FLOOR, //138
			FLOOR, //139
			FLOOR, //140
			FLOOR, //141
			FLOOR, //142
			FLOOR, //143
			FLOOR, //144
			FLOOR, //145
			FLOOR, //146
			FLOOR, //147
			FLOOR, //148
			FLOOR, //149
			FLOOR, //150
			FLOOR, //151
			FLOOR, //152
			FLOOR, //153
			FLOOR, //154
			FLOOR, //155
			FLOOR, //156
			FLOOR, //157
			FLOOR, //158
			FLOOR, //159
			FLOOR, //160
			FLOOR, //161
			FLOOR, //162
			FLOOR, //163
			FLOOR, //164
			FLOOR, //165
			FLOOR, //166
			FLOOR, //167
			FLOOR, //168
			FLOOR, //169
			FLOOR, //170
			FLOOR, //171
			FLOOR, //172
			FLOOR, //173
			FLOOR, //174
			FLOOR, //175
			FLOOR, //176
			FLOOR, //177
			FLOOR, //178
			FLOOR, //179
			FLOOR, //180
			FLOOR, //181
			FLOOR, //182
			FLOOR, //183
			FLOOR, //184
			FLOOR, //185
			FLOOR, //186
			FLOOR, //187
			FLOOR, //188
			FLOOR, //189
			FLOOR, //190
			FLOOR, //191
			FLOOR, //192
			FLOOR, //193
			FLOOR, //194
			FLOOR, //195
			FLOOR, //196
			FLOOR, //197
			FLOOR, //198
			FLOOR, //199
			FLOOR, //200
			FLOOR, //201
			FLOOR, //202
			FLOOR, //203
			FLOOR, //204
			FLOOR, //205
			FLOOR, //206
			FLOOR, //207
			FLOOR, //208
			FLOOR, //209
			FLOOR, //210
			FLOOR, //211
			FLOOR, //212
			FLOOR, //213
			FLOOR, //214
			FLOOR, //215
			FLOOR, //216
			FLOOR, //217
			FLOOR, //218
			FLOOR, //219
			FLOOR, //220
			FLOOR, //221
			FLOOR, //222
			FLOOR, //223
			FLOOR, //224
			FLOOR, //225
			FLOOR, //226
			FLOOR, //227
			FLOOR, //228
			FLOOR, //229
			FLOOR, //230
			FLOOR, //231
			FLOOR, //232
			FLOOR, //233
			FLOOR, //234
			FLOOR, //235
			FLOOR, //236
			FLOOR, //237
			FLOOR, //238
			FLOOR, //239
			FLOOR, //240
			FLOOR, //241
			FLOOR, //242
			FLOOR, //243
			FLOOR, //244
			FLOOR, //245
			FLOOR, //246
			FLOOR, //247
			FLOOR, //248
			FLOOR, //249
			FLOOR, //250
			FLOOR, //251
			FLOOR, //252
			FLOOR, //253
			FLOOR, //254
			FLOOR, //255

	};
	
	protected ArrayList<Entity> ledger;
	
	private World next;
	
	public World(String path, EntityList e, int d) {
		ID = d;
		entityList = e;
		BufferedImage img;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException ex) {
			img = null;
			ex.printStackTrace();
		}
		width = img.getWidth();
		height = img.getHeight();
		map = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = img.getRGB(i, j);
				int r = ((rgb >> 16) & 0xff);
				map[i][j]= reference[r];
			}
		}
		ledger = new ArrayList<Entity>();
	}
	
	public void setnext(World w) {
		next = w;
	}
	
	public int access(int x, int y) {
		if(x < 0) {
			x = 0;
		}else if(x >= width) {
			x = width-1;
		}
		if(y < 0) {
			y = 0;
		}else if(y >= height) {
			y = height-1;
		}
		return map[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ArrayList<Entity> getKeys() {
		return keys;
	}
	
	protected void setKeys(ArrayList<Entity> k) {
		keys = k;
	}

	abstract public void begin(GameScene gameScene);
	
	abstract public void update(GameScene gameScene);
	
	public int getID() {
		return ID;
	}
	
	public World getNext() {
		return next;
	}
}
