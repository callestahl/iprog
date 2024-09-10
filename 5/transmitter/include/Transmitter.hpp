#pragma once
#include <WiFi.h>
#include <WiFiUdp.h>

#include "WifiSettings.hpp"

class Transmitter {
 private:
  // UDP instance for sending data
  WiFiUDP udp;

 public:
  /**
   * @brief construct a new Transmitter object
   */
  Transmitter();

  /**
   * @brief connect to the wifi network
   */
  void connectToWifi();

  /**
   * @brief send a float value as a UDP packet
   *
   * @param data the float value to be sent
   */
  void sendData(float data);
};
