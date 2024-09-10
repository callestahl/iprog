#include "Receiver.hpp"

void Receiver::connectToWifi() {
  Serial.print("Connecting to ");
  Serial.println(ssid);

  // start connection to wifi and wait until connected
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("Wifi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // open the port
  udp.begin(port);
  Serial.print("Listening on port ");
  Serial.println(port);
}

float Receiver::receiveData() {
  // buffer for incoming data
  uint8_t incomingPacket[255];

  int packetSize = udp.parsePacket();
  // packet exists
  if (packetSize) {
    // read data into buffer
    int length = udp.read(incomingPacket, 255);
    // data was read
    if (length > 0) {
      // contains float
      if (length == sizeof(float)) {
        // convert received data to float and return it
        float receivedValue;
        memcpy(&receivedValue, incomingPacket, sizeof(float));
        return receivedValue;
      }
    }
  }
  // return -1.0f to indicate that no valid packet was received
  return -1.0f;
}
