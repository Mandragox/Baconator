package com.bacon.Baconator;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.reactivex.Observable;

@Controller
public class BaconController 
{
    @GetMapping("/give-me-bacon/{howmuch}")
    public String homePage(Model model, @PathVariable Long howmuch) 
    {        
        Date start =  new Date();
        String output = "{<br>";
        output += "&emsp;\"runId\" : " + UUID.randomUUID() +  ",<br>";
        output += "&emsp;\"start\": " + Timestamp.from(start.toInstant()) + ",<br>";
        output += "&emsp;\"end\": ";
        
        String data = ",<br>" + "&emsp;\"items\" [<br>";
        
        //Generating items
        try
        {
        	for(int i = 0; i < howmuch; i++)
        	{
            	data += BaconHandler.ProduceBacon();
        		if(i+1 != howmuch)
        		{
        			data += ",<br>";
        		}
        		else
        		{
        		data+= "<br>";
        		}
        	}
        }
        catch(IOException E)
        {
        	return "Error";
        }
        
        data += "&emsp;&emsp;]<br>";
        data += "}<br>";        
        Date end =  new Date();        
        output += Timestamp.from(end.toInstant()) + data;        
        
        model.addAttribute("output", output);
        return "home";
    }
}
