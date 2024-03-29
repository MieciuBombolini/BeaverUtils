package dev.bebomny.beaver.beaverutils.helpers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.bebomny.beaver.beaverutils.client.BeaverUtilsClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.BufferBuilder.BuiltBuffer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

import java.awt.*;

public class RenderUtils {

    public static int getRainbowColorAsInt() {
        float[] rainbow = getRainbowColor();
        return (int)rainbow[0] | (int)rainbow[1] << 8 | (int)rainbow[2] << 16;
    }

    public static float[] getRainbowColor() {
        float x  = System.currentTimeMillis() % 2000 / 1000F;

        float[] rainbow = new float[3];
        rainbow[0] = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
        rainbow[1] = 0.5F + 0.5F * MathHelper.sin((x + 4F/3F) * (float)Math.PI);
        rainbow[2] = 0.5F + 0.5F * MathHelper.sin((x + 8F/3F) * (float)Math.PI);
        return rainbow;
    }

    public static void drawOutlinedBox(MatrixStack matrixStack) {
        drawOutlinedBox(new Box(0,0,0,1,1,1), matrixStack);
    }

    public static void drawOutlinedBox(Box box, VertexBuffer vertexBuffer) {
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);
        drawOutlinedBox(box, bufferBuilder);
        BuiltBuffer buffer = bufferBuilder.end();

        vertexBuffer.bind();
        vertexBuffer.upload(buffer);
        VertexBuffer.unbind();
    }

    public static void drawOutlinedBox(Box box, MatrixStack matrixStack) {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        //should be 12
        //example for a box with line lengths of 1
        //0,0,0 -> 1,0,0
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
        //0,0,0 -> 0,1,0
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
        //0,0,0 -> 0,0,1
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
        //1,0,0 -> 1,0,1
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
        //0,0,1 -> 1,0,1
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
        //1,0,0 -> 1,1,0
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
        //1,0,1 -> 1,1,1
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
        //0,0,1 -> 0,1,1
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).next();
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
        //0,1,0 -> 0,1,1
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
        //0,1,0 -> 1,1,0
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
        //0,1,1 -> 1,1,1
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();
        //1,1,0 -> 1,1,1
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).next();
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).next();

        tessellator.draw();
    }

    public static void drawOutlinedBox(Box box, BufferBuilder bufferBuilder) {
        //should be 12
        //example for a box with line lengths of 1
        //0,0,0 -> 1,0,0
        bufferBuilder.vertex(box.minX, box.minY, box.minZ).next();
        bufferBuilder.vertex(box.maxX, box.minY, box.minZ).next();
        //0,0,0 -> 0,1,0
        bufferBuilder.vertex(box.minX, box.minY, box.minZ).next();
        bufferBuilder.vertex(box.minX, box.maxY, box.minZ).next();
        //0,0,0 -> 0,0,1
        bufferBuilder.vertex(box.minX, box.minY, box.minZ).next();
        bufferBuilder.vertex(box.minX, box.minY, box.maxZ).next();
        //1,0,0 -> 1,0,1
        bufferBuilder.vertex(box.maxX, box.minY, box.minZ).next();
        bufferBuilder.vertex(box.maxX, box.minY, box.maxZ).next();
        //0,0,1 -> 1,0,1
        bufferBuilder.vertex(box.minX, box.minY, box.maxZ).next();
        bufferBuilder.vertex(box.maxX, box.minY, box.maxZ).next();
        //1,0,0 -> 1,1,0
        bufferBuilder.vertex(box.maxX, box.minY, box.minZ).next();
        bufferBuilder.vertex(box.maxX, box.maxY, box.minZ).next();
        //1,0,1 -> 1,1,1
        bufferBuilder.vertex(box.maxX, box.minY, box.maxZ).next();
        bufferBuilder.vertex(box.maxX, box.maxY, box.maxZ).next();
        //0,0,1 -> 0,1,1
        bufferBuilder.vertex(box.minX, box.minY, box.maxZ).next();
        bufferBuilder.vertex(box.minX, box.maxY, box.maxZ).next();
        //0,1,0 -> 0,1,1
        bufferBuilder.vertex(box.minX, box.maxY, box.minZ).next();
        bufferBuilder.vertex(box.minX, box.maxY, box.maxZ).next();
        //0,1,0 -> 1,1,0
        bufferBuilder.vertex(box.minX, box.maxY, box.minZ).next();
        bufferBuilder.vertex(box.maxX, box.maxY, box.minZ).next();
        //0,1,1 -> 1,1,1
        bufferBuilder.vertex(box.minX, box.maxY, box.maxZ).next();
        bufferBuilder.vertex(box.maxX, box.maxY, box.maxZ).next();
        //1,1,0 -> 1,1,1
        bufferBuilder.vertex(box.maxX, box.maxY, box.minZ).next();
        bufferBuilder.vertex(box.maxX, box.maxY, box.maxZ).next();
    }
}
