// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('@grpc/grpc-js');
var sequence_generator_pb = require('./sequence_generator_pb.js');

function serialize_SequenceRequest(arg) {
  if (!(arg instanceof sequence_generator_pb.SequenceRequest)) {
    throw new Error('Expected argument of type SequenceRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_SequenceRequest(buffer_arg) {
  return sequence_generator_pb.SequenceRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_SequenceResponse(arg) {
  if (!(arg instanceof sequence_generator_pb.SequenceResponse)) {
    throw new Error('Expected argument of type SequenceResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_SequenceResponse(buffer_arg) {
  return sequence_generator_pb.SequenceResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


var SequenceGeneratorService = exports.SequenceGeneratorService = {
  generateSequence: {
    path: '/SequenceGenerator/GenerateSequence',
    requestStream: false,
    responseStream: false,
    requestType: sequence_generator_pb.SequenceRequest,
    responseType: sequence_generator_pb.SequenceResponse,
    requestSerialize: serialize_SequenceRequest,
    requestDeserialize: deserialize_SequenceRequest,
    responseSerialize: serialize_SequenceResponse,
    responseDeserialize: deserialize_SequenceResponse,
  },
};

exports.SequenceGeneratorClient = grpc.makeGenericClientConstructor(SequenceGeneratorService);
