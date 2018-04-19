package com.davidgyoung.alexa.busstatus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.json.SpeechletRequestEnvelope;

public class BusStatusSpeechlet implements SpeechletV2 {
    private static final Logger log = LoggerFactory.getLogger(BusStatusSpeechlet.class);

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Welcome to the bus status skill.");

        return SpeechletResponse.newAskResponse(speech, null, null);                
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {    		
        IntentRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        
        Intent intent = request.getIntent();
        if ("BusStatusIntent".equals(intent.getName())) {
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("You have triggered the bus status intent.  I have no answer for you yet.");

            return SpeechletResponse.newAskResponse(speech, null, null);                        	
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
}
