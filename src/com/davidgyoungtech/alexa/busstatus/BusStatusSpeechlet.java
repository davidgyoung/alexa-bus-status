package com.davidgyoungtech.alexa.busstatus;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.json.SpeechletRequestEnvelope;

public class BusStatusSpeechlet implements SpeechletV2 {
    private static final Logger log = LoggerFactory.getLogger(BusStatusSpeechlet.class);
    
    String[][] dailyBusSchedule  = new String[][] {
    		{ "BWI Airport", "07:30" },
    		{ "BWI Airport", "08:30" },
    		{ "BWI Airport", "12:30" },
    		{ "BWI Airport", "15:30" },
    		{ "BWI Airport", "20:45" },
    		{ "Washington Union Station", "07:10" },
    		{ "Washington Union Station", "08:40" },
    		{ "Washington Union Station", "09:10" },
    		{ "Washington Union Station", "10:40" },
    		{ "Washington Union Station", "11:10" },
    		{ "Washington Union Station", "12:40" },
    		{ "Washington Union Station", "13:10" }    	
    };
    
    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        return getTallResponse("Welcome to the bus status skill.");         
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {    		
        IntentRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        
        Intent intent = request.getIntent();
        if ("BusStatusIntent".equals(intent.getName())) {
        		Slot slot = intent.getSlots().get("Destination");        		
        		String firstDailyDepartureTime = null;
        		String nextDepartureTime = null;    
        		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        		timeFormatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        		String now = timeFormatter.format(new Date());
        		log.debug("now is "+now);
        		if (slot != null) {
            		String destination = slot.getValue();            		
            		for (String[] scheduleItem : dailyBusSchedule) {
            			String scheduleDestination = scheduleItem[0];
            			String scheduleDepartureTime = scheduleItem[1];
            			if (scheduleDestination.equalsIgnoreCase(destination)) {
            				if (firstDailyDepartureTime == null) {
            					firstDailyDepartureTime = scheduleDepartureTime;
            				}
            				// If nextDepartureTime is not yet set and the
            				// scheduleDeparture time is in the future use it is
            				// the nextDepartureTime
            				if (nextDepartureTime == null &&
            					scheduleDepartureTime.compareTo(now) > 0) {
            					nextDepartureTime = scheduleDepartureTime;	
            					log.debug("time is in the future: "+scheduleDepartureTime);            					
            				}
            				else {
            	        			log.debug("time is in the past: "+scheduleDepartureTime);            					
            				}
            			}            			
            		}
            		if (firstDailyDepartureTime == null) {
            			return getAskResponse("I don't know about departures to "+destination+".  Try a different destination.");        			
            		}
            		else if (nextDepartureTime == null) {
            			return getAskResponse("The next bus for "+destination+" is tomorrow at "+firstDailyDepartureTime);        			            			
            		}
            		else {
            			return getAskResponse("The next bus for "+destination+" is today at "+nextDepartureTime);        			            			            			
            		}

        		}
    			return getAskResponse("Please specify a destination.");        			
        }
        else {
            throw new IllegalArgumentException("Unrecognized intent: " + intent.getName());
        }
    } 
    
    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
    }    
    
    private SpeechletResponse getAskResponse(String message) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(message);
        SimpleCard card = new SimpleCard();
        card.setTitle("David's Bus Status");
        card.setContent(message);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Sorry, I didn't get that.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return SpeechletResponse.newAskResponse(speech, reprompt, card);      	
    }
    private SpeechletResponse getTallResponse(String message) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(message);
        SimpleCard card = new SimpleCard();
        card.setTitle("David's Bus Statuss");
        card.setContent(message);
        return SpeechletResponse.newTellResponse(speech, card);
    }


}
