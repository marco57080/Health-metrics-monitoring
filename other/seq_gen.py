import numpy as np
import grpc
from concurrent import futures
from sequence_generator_pb2 import SequenceRequest, SequenceResponse
from sequence_generator_pb2_grpc import SequenceGeneratorServicer, add_SequenceGeneratorServicer_to_server


import h5py
import random


class SequenceGeneratorService(SequenceGeneratorServicer):    
    def __init__(self):
        self.__tracing_np_array = None
        self.__load_tracing_data()

    def __load_tracing_data(self):
        with h5py.File("ecg_tracings.hdf5", "r") as f:
            self.__tracing_np_array = np.array(f['tracings'])

    def GenerateSequence(self, request, context):
        # length = request.length
        # sequence = np.random.rand(length)
        sequence = self.__tracing_np_array[101,:,1]
        return SequenceResponse(sequence=sequence)

server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
add_SequenceGeneratorServicer_to_server(SequenceGeneratorService(), server)
server.add_insecure_port('[::]:50051')
server.start()
server.wait_for_termination()