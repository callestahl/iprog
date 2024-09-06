#include <Arduino.h>

#include "Receiver.hpp"

Receiver receiver;

void setup() {
  Serial.begin(4800);
  receiver.connectToWifi();
}

void loop() {
  float receivedData = receiver.receiveData();
  if (receivedData >= 0.0f) {
    Serial.println(receivedData);
  }
}