/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinggi;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Cool
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat citra = Imgcodecs.imread("R_200cm.jpg", Imgproc.COLOR_BGR2GRAY);
        //    Mat crop = Imgcodecs.imread("cropped.jpg", Imgproc.COLOR_BGR2GRAY);

        kontur(citra);

        //  pangkas(crop);
    }

    public static void kontur(Mat citra) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat badan1 = citra.clone();
        Imgproc.cvtColor(citra, citra, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.adaptiveThreshold(badan, badan, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,19, 14);

        Imgproc.threshold(citra, citra, 0, 255, Imgproc.THRESH_OTSU);
//        Imgproc.threshold(badan, badan, 0, 255, Imgproc.THRESH_BINARY);
        Imgproc.medianBlur(citra, citra, 21);
        Imgproc.Canny(citra, citra, 0, 255);

        //Imgcodecs.imwrite("hasil.jpg", badan);
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(citra, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat drawing = Mat.zeros(citra.size(), CvType.CV_8UC1);

        Rect rect;

        double BK, c;
        int paling_tinggi = 0, paling_lebar = 0, kordinat_y = 0, kordinat_x = 0, paling_atas = citra.rows(), tinggi_palingAtas = 0;
        String br;
        Scalar color = new Scalar(255);
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(drawing, contours, i, color);

            rect = Imgproc.boundingRect(contours.get(i));

            //    System.out.println(rect);
            Imgproc.rectangle(drawing, rect.tl(), rect.br(), new Scalar(255), 4);
            int tinggi = rect.height;
            int lebar = rect.width;

            if (paling_lebar < rect.width) {
                paling_lebar = rect.width;
                kordinat_x = rect.x;
            }
            if (paling_tinggi < rect.height) {
                paling_tinggi = rect.height;
                kordinat_y = rect.y;
            }

            if (paling_atas > rect.y) {
                paling_atas = rect.y;
                tinggi_palingAtas = rect.height;
            }

        }

        Imgcodecs.imwrite("hasil.jpg", drawing);
      //  Cropping
//            BK = kordinat_y + paling_tinggi + 157.914;
//            c = citra.height() - (BK);
//            int cc = (int)c;
//
//            int lbr = drawing.cols();
//            int tgg = drawing.rows(); 
//
//            Rect roi = new Rect(0, 0, lbr, tgg - cc);
//            Mat cropped = new Mat(drawing, roi);
//        Imgcodecs.imwrite("cropped.jpg", cropped);

        System.out.println("tinggi objek piksel manusia : " + paling_tinggi);
        //System.out.println("objek paling lebar " + paling_lebar);
        //System.out.println("kordinat x " + kordinat_x);
        //System.out.println("kodinat y " + kordinat_y);
        System.out.println("kordinat y objek paling atas " + paling_atas);
        System.out.println("tinggi objek paling atas    : " + tinggi_palingAtas);
        System.out.println("Tinggi citra                : " + citra.height());

        System.out.println("tinggi px stiker            : " + (citra.height() - paling_atas));

        double Tps = citra.height() - paling_atas, Tm, Tpm = paling_tinggi, PerCm;

        PerCm = 200 / Tps;

        Tm = Tpm * PerCm;

        System.out.println("1 Piksel per Cm             : " + PerCm);
        System.out.println("TINGGI BADAN MANUSIA        : " + Tm + " Cm");

    }

//    private static void pangkas(Mat crop){
//        
//        Mat crop1 = crop.clone();
//        
//        Mat hierarchy = new Mat();
//        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
//        Imgproc.findContours(crop, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        Mat cropping = Mat.zeros(crop.size(), CvType.CV_8UC1);
//        
//        Rect rect;
//        
//        double BK, c;
//        int paling_tinggi = 0, paling_lebar = 0, kordinat_y = 0, kordinat_x = 0, paling_atas = crop.rows(), tinggi_palingAtas = 0;
//        String br;
//        Scalar color = new Scalar(255);
//            for (int i = 0; i < contours.size(); i++) {
//            Imgproc.drawContours(cropping, contours, i, color);
//
//
//            rect = Imgproc.boundingRect(contours.get(i));
//            
//
//
//            //    System.out.println(rect);
//            Imgproc.rectangle(cropping, rect.tl(), rect.br(), new Scalar(255), 4);
//            int tinggi = rect.height;
//            int lebar = rect.width;
//
//            if (paling_lebar < rect.width) {
//                paling_lebar = rect.width;
//                kordinat_x = rect.x;
//            }
//            if (paling_tinggi < rect.height) {
//                paling_tinggi = rect.height;
//                kordinat_y = rect.y;
//
//            }
//
//            if (paling_atas > rect.y) {
//                paling_atas = rect.y;
//                tinggi_palingAtas = rect.height;
//            }
//
//        }
//            Imgcodecs.imwrite("hasilAkhir.jpg", cropping);
//            
//                    System.out.println("tinggi objek piksel manusia : " + paling_tinggi);
//        //System.out.println("objek paling lebar " + paling_lebar);
//        //System.out.println("kordinat x " + kordinat_x);
//        //System.out.println("kodinat y " + kordinat_y);
//        System.out.println("kordinat y objek paling atas " + paling_atas);
//        System.out.println("tinggi objek paling atas    : " + tinggi_palingAtas);
//        System.out.println("Tinggi citra                : " + crop.height());
//
//        System.out.println("tinggi px stiker            : " + (crop.height() - paling_atas));
//
//        double Tps = crop.height() - paling_atas, Tm, Tpm = paling_tinggi, PerCm;
//
//        PerCm = 200 / Tps;
//
//        Tm = Tpm * PerCm;
//
//        System.out.println("1 Piksel per Cm             : " + PerCm);
//        System.out.println("TINGGI BADAN MANUSIA        : " + Tm + " Cm");
//    }
}
