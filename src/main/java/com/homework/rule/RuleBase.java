package com.homework.rule;

import java.util.HashMap;
import java.util.List;

import com.homework.dto.LogBase;

public interface RuleBase {

	HashMap<String, Boolean> fraudCheck(final List<LogBase> logList);
}
