package com.face.detection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class faceDetection {


    public static void main (String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("holaaaa");

        Mat image = Imgcodecs.imread("images/hermanos.jpg");

        //create method for detect and save
        detectAndSave(image);
    }

    public static void detectAndSave(Mat image){
        //create some objects
        MatOfRect faces = new MatOfRect(); //store more than one face

        //convert to gray scale
        Mat grayframe = new Mat();
        Imgproc.cvtColor(image, grayframe, Imgproc.COLOR_BGR2GRAY);

        //improve contrast for better result
        Imgproc.equalizeHist(grayframe, grayframe);

        int height = grayframe.height();
        int absoluteFaceSize = 0;
        if(Math.round(height * 0.2f) > 0){
            absoluteFaceSize = Math.round(height * 0.2f);

        }

        //detect faces
        CascadeClassifier faceCascade = new CascadeClassifier();

        //lode trained data file
        faceCascade.load("data/haarcascade_frontalface_alt2.xml");
        faceCascade.detectMultiScale(grayframe, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        //write to file
        Rect[] faceArray = faces.toArray();
        for(int i = 0 ; i < faceArray.length ; i++){
            //draw rect
            Imgproc.rectangle(image, faceArray[i], new Scalar(0,0,255),3);
        }
        Imgcodecs.imwrite("images/output.jpg", image);
        System.out.println("write success" + faceArray.length);

    }
}
