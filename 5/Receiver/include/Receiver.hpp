#pragma once
#include <Arduino.h>
#include <WiFi.h>
#include <WiFiUdp.h>

#include "WifiSettings.hpp"

/**
 * @class Receiver
 * @brief class to handle wifi and UDP communication for receiving data
 */
class Receiver {
 private:
  // UDP instance for receiving data
  WiFiUDP udp;

 public:
  /**
   * @brief connects to wifi and opens a port to receive data
   */
  void connectToWifi();

  /**
   * @brief receives data over UDP
   * @return a float value received from the UDP packet or -1.0f if no valid
   * data is received
   */
  float receiveData();
};
