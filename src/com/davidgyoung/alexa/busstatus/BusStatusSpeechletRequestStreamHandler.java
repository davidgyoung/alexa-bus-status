package com.davidgyoung.alexa.busstatus;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class BusStatusSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds;

	static {
    supportedApplicationIds = new HashSet<String>();
    // This value must be copied from your skill configuration
    // Go to: https://developer.amazon.com/alexa/console/ask/build
    // Then click the endpoint section and copy the skill id then paste it below.
    supportedApplicationIds.add("amzn1.ask.skill.a780447f-537e-4b0d-8ff8-51de06655ae5");
	}

	public BusStatusSpeechletRequestStreamHandler() {
		super(new BusStatusSpeechlet(), supportedApplicationIds);
	}
}
