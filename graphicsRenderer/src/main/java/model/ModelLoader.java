package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class ModelLoader
{
    public static Model fromFile(Path path) throws IOException
    {
        Map<Integer, Model.Vertex> vertices = new HashMap<>();
        List<Model.Face> faces = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        Files.readAllLines(path).stream().forEach((str) -> {
            String[] spt = str.split(" ");
            switch (spt[0]) {
                case "v":
                    vertices.put(counter.incrementAndGet(), new Model.Vertex(
                            Double.parseDouble(spt[1]),
                            Double.parseDouble(spt[2]),
                            Double.parseDouble(spt[3])
                    ));
                    break;
                case "f":
                    faces.add(new Model.Face(
                            Integer.parseInt(spt[1].split("/")[0]),
                            Integer.parseInt(spt[2].split("/")[0]),
                            Integer.parseInt(spt[3].split("/")[0])
                    ));
                    break;
            }
        });

        return new Model(vertices, faces);
    }
}
