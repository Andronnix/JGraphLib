package model;

import geometry.Vector3d;
import geometry.Vector3d;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class ModelLoader
{
    public static Model fromFile(Path model, Path textureImg) throws IOException
    {
        Map<Integer, Vector3d> vertices = new HashMap<>();
        Map<Integer, Vector3d> texture = new HashMap<>();
        List<Model.Face> faces = new ArrayList<>();
        AtomicInteger vCounter = new AtomicInteger(0);
        AtomicInteger tCounter = new AtomicInteger(0);

        BufferedImage tImg = TGALoader.loadTGA(textureImg.toString());
        int textureWidth  = tImg.getWidth();
        int textureHeight = tImg.getHeight();

        Files.readAllLines(model).stream().forEach((str) -> {
            String[] spt = str.split("\\s+");
            switch (spt[0]) {
                case "v":
                    vertices.put(vCounter.incrementAndGet(), new Vector3d(
                            Double.parseDouble(spt[1]),
                            Double.parseDouble(spt[2]),
                            Double.parseDouble(spt[3])
                    ));
                    break;
                case "vt":
                    texture.put(tCounter.incrementAndGet(), new Vector3d(
                            Double.parseDouble(spt[1]) * textureWidth,
                            (1 - Double.parseDouble(spt[2])) * textureHeight,
                            0
                    ));
                    break;
                case "f":
                    faces.add(new Model.Face(
                            Integer.parseInt(spt[1].split("/")[0]), //vertices
                            Integer.parseInt(spt[2].split("/")[0]),
                            Integer.parseInt(spt[3].split("/")[0]),
                            Integer.parseInt(spt[1].split("/")[1]),  //texture
                            Integer.parseInt(spt[2].split("/")[1]),
                            Integer.parseInt(spt[3].split("/")[1])
                    ));
                    break;
            }
        });

        return new Model(vertices, texture, faces, tImg);
    }
}
