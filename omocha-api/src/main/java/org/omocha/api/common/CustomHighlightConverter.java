package org.omocha.api.common;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class CustomHighlightConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

	@Override
	protected String getForegroundColorCode(ILoggingEvent event) {
		return switch (event.getLevel().toString()) {
			case "ERROR" -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
			case "WARN" -> ANSIConstants.BOLD + ANSIConstants.YELLOW_FG;
			case "INFO" -> ANSIConstants.BOLD + ANSIConstants.GREEN_FG;
			case "DEBUG" -> ANSIConstants.BOLD + ANSIConstants.BLUE_FG;
			default -> ANSIConstants.DEFAULT_FG;
		};
	}
}
