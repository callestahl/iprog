#include <WiFi.h>
#include <WiFiUdp.h>

// WiFi credentials
const char* ssid = "your_SSID";
const char* password = "your_PASSWORD";

// UDP settings
WiFiUDP udp;
unsigned int localUdpPort = 12345;  // local port to listen on
char incomingPacket[255];  // buffer for incoming packets

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
  udp.begin(localUdpPort);
  Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
}

void loop() {
  int packetSize = udp.parsePacket();
  if (packetSize) {
    int len = udp.read(incomingPacket, 255);
    if (len > 0) {
      incomingPacket[len] = 0;
    }
    Serial.printf("Received packet of size %d\n", packetSize);
    Serial.printf("UDP packet contents: %s\n", incomingPacket);

    // Convert the packet to a float
    float receivedValue = atof(incomingPacket);
    Serial.printf("Converted float value: %f\n", receivedValue);

    // Process the float value as needed
  }
}