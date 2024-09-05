#pragma once
#include <WiFi.h>
#include <WiFiUdp.h>
#include "WifiSettings.hpp"

class Transmitter
{
private:
    WiFiUDP udp;
    
public:
    Transmitter();
    void connectToWifi();
    void sendData(float data);
};

