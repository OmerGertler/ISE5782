package scene;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import parser.SceneDescriptor;

/**
 * This class is used to store the information of the scene.
 * open a json file and parse it to the scene.
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class SceneBuilder {
    String filePath;
    Scene scene;
    SceneDescriptor sceneDescriptor;

    /**
     * Scene default constructor
     * set the scene name and the file path
     */

    public SceneBuilder(String filePath) {
        this.filePath = filePath;
        scene = new Scene("");
    }

    /**
     * get the scene
     * 
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * THis function load the json file and parse it to the scene.
     * 
     */
    public void loadSceneFromFile() {
        try {
            // read the file
            FileReader fileReader = new FileReader(filePath);
            // create the parser
            JSONParser parser = new JSONParser();
            // parse the file
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            // parse the scene
            sceneDescriptor = new SceneDescriptor(jsonObject);
            scene = sceneDescriptor.getScene();
        } catch (FileNotFoundException e) {
          throw new RuntimeException("File Not Found");
        } catch (IOException e) {
            throw new RuntimeException("IOExpetion");
        } catch (ParseException e) {
            throw new RuntimeException("PArser Exption");
        }
    }

}
