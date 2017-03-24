package com.abbvie.productvisibility.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.abbvie.productvisibility.to.FileUploadTO;

@Path("/file")
public class UploadFileService {

@POST
@Path("/upload")
@Consumes({ MediaType.APPLICATION_JSON })
public Response uploadFile(FileUploadTO fileTO){

//	@FormDataParam("file") byte[] uploadedfile,
//    @FormDataParam("file") FormDataContentDisposition fileDetail,
//    @FormDataParam("path") String path)

    // Path format //10.217.14.97/Installables/uploaded/
    System.out.println("path::"+fileTO.getPath());
    String uploadedFileLocation = fileTO.getPath()+ fileTO.getFileName();

    // save it
    boolean success=writeToFile(fileTO.getFileContent(), uploadedFileLocation);

    String output = null;
    
    if(success) {
    	output= "File uploaded to : " + uploadedFileLocation;
    }else{
    	output="File upload Failed";
    }

    return Response.status(200).entity(output).build();

}

// save uploaded file to new location
public boolean writeToFile(byte[] uploadedfile,
        String uploadedFileLocation) 
{
		boolean uploadSuccess=false;
	    try 
	    {
	        OutputStream out = new FileOutputStream(new File(
	                uploadedFileLocation));
	//        int read = 0;
	//        byte[] bytes = new byte[1024];
	
	        //out = new FileOutputStream(new File(uploadedFileLocation));
	        IOUtils.write(uploadedfile, out);
	        uploadSuccess=true;
	       // out.write(uploadedfile);
	//        while ((read = uploadedInputStream.read(bytes)) != -1) {
	//            out.write(bytes, 0, read);
	//        }
	        out.flush();
	        out.close();
	    } catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return uploadSuccess;
   }

   }