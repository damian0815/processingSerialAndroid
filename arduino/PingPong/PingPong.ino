void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available() >= 4) {
    char input[4];
    Serial.readBytes(input, 4);

    char output[4];
    output[0] = input[0];
    output[1] = input[1];
    output[2] = '*';
    output[3] = '*';
    Serial.write(output, 4);
  }
}

