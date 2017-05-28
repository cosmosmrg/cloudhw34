package mydropbox;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.*;

/**
 * Created by May on 5/21/2017 AD.
 */
public class Bucket {
    private static AmazonS3 s3Client;
    final static String BUCKETNAME = "mydropbox5681091";
    public Bucket(){
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
        createBucket(BUCKETNAME); //create only one time

    }
    private static boolean createBucket (String bucketName) {
        try {
            // Check if bucket exists, and if does not exist create new bucket named bucketName in S3
            if(!s3Client.doesBucketExist(bucketName)){
                s3Client.createBucket(bucketName);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    // Add the object in filePath on my computer to the bucketName bucket on S3, using the key keyName for the object
    // Catch all exceptions, and print error to stdout (System.out)
    public static boolean addObjectToBucket(String username,String keyName, String filePath) {

        TransferManager tm = new TransferManager(s3Client);
        Upload upload = tm.upload(BUCKETNAME,username+"-"+keyName,new File(filePath));
        // Use TransferManager to upload file to S3
        try {
            upload.waitForCompletion();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // List all objects in the bucketName bucket
    // Catch all exceptions, and print error to stdout (System.out)
    public static boolean viewObjectsInBucket(String username) {

        try {
            ObjectListing objL = s3Client.listObjects(BUCKETNAME);
            System.out.println("OK");
            for(S3ObjectSummary objectSummary:objL.getObjectSummaries()){
                String k = objectSummary.getKey();
                if(k.substring(0,k.indexOf('-')).equals(username)){
                    System.out.println(k.substring(username.length()+1)+" "+objectSummary.getSize()+" "+objectSummary.getLastModified());
                }
            }
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    public static boolean downloadFile(String username,String filename) {
        try{
            S3Object object = s3Client.getObject(new GetObjectRequest(BUCKETNAME, username+"-"+filename));
            InputStream reader = new BufferedInputStream(
                    object.getObjectContent());
            File file = new File(filename);
            OutputStream writer = new BufferedOutputStream(new FileOutputStream(file));

            int read = -1;

            while ( ( read = reader.read() ) != -1 ) {
                writer.write(read);
            }

            writer.flush();
            writer.close();
            reader.close();
            return true;
        }
        catch (Exception e){
            if(!s3Client.doesObjectExist(BUCKETNAME,username+"-"+filename)){
                System.out.println("File does not exist");
            }
            return false;
        }

    }
}
