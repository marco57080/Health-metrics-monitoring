const { Kafka } = require('kafkajs')

const kafka = new Kafka({brokers: ['localhost:29092'],})
const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const PROTO_PATH = 'sequence_generator.proto';
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true
});
const SequenceGeneratorService = grpc.loadPackageDefinition(packageDefinition).SequenceGenerator;
const client = new SequenceGeneratorService('localhost:50051', grpc.credentials.createInsecure());

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}


async function produceNormalHealthRecordMessage(pass_in_id,period_in_ms, kafka_topic) {
    try {    
        const producer = kafka.producer()
        await producer.connect();

        let ecg_sequence = new Array(1000).fill(0)
        let ecg_sequence_length = 4096
        let counter = 0

        client.GenerateSequence({ length: 10 }, (error, response) => {        
            ecg_sequence = response.sequence
        })

        if(counter < ecg_sequence_length){         
            setInterval(async () => {            
                if (counter > 4096){
                    counter = 0
                    console.log("Finished")
                }
                if (ecg_sequence.length != ecg_sequence_length && ecg_sequence.length != 0){
                    ecg_sequence_length = ecg_sequence.length
                }
                await producer.send({
                topic: kafka_topic,
                messages: [
                    {
                        key: null,
                        value: JSON.stringify(
                        {
                            citizenid: pass_in_id,
                            timestamp: new Date().getTime(), 
                            heart_rate: getRandomNumber(60,200),
                            blood_pressure_diastolic: getRandomNumber(80,120),
                            blood_pressure_systolic: getRandomNumber(120,180),
                            spo2: getRandomNumber(85,100),
                            ECG: ecg_sequence[counter],
                            record_type: 0
                        }
                        ),
                    }
                ]});      
                counter++
            },period_in_ms)
        }        
    }catch (error) {
        console.error('Error producing messages:', error)
    }
}


produceNormalHealthRecordMessage(123,2.5,"healthRecord-topic")
produceNormalHealthRecordMessage(456,2.5,"healthRecord-topic")




