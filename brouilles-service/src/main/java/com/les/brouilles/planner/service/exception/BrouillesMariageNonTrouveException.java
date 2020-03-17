package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesMariageNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le mariage avec id {0} n'a pas été trouvé";

	public BrouillesMariageNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}
