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

client.GenerateSequence({ length: 10 }, (error, response) => {
  if (error) {
    console.error(error);
    return;
  }
  console.log(response.sequence);
});