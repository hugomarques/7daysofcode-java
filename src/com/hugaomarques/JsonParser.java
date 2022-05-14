package com.hugaomarques;

import java.util.List;

public interface JsonParser {
  List<? extends Content> parse();
}
