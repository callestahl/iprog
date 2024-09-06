#pragma once
#include <Arduino.h>
#include <WiFi.h>
#include <WiFiUdp.h>

#include "WifiSettings.hpp"

class Receiver {
 private:
  WiFiUDP udp;

 public:
  void connectToWifi();
  float receiveData();
};
