
# Health metrics monitoring Web App

https://github.com/user-attachments/assets/64f7bfb8-e54d-488f-a0c7-6f85002096fb

## Quick Start

```
docker-compose up -d
```

Run the frontend

```
npm install
npm run dev
```

Run the backend

```
mvn spring-boot:run
```

To test the functionality of the data streaming and monitoring service, start the gRPC server first

```
npm install
python install -r requirement.txt
python seq_gen.py
node seq_gen.js
```

Next, create stream and table manually in ksqlDB cli, refer to `ksqlDB_init.sql`

To run the monitoring service and test it with simulated input, run the following scripts with Node.js

```
node kafka_consumer_metric_monitoring.js
node kafka_producer_healthRecord.js
```

To adjust the frequency of the simulated signal, modify the function arguments in `kafka_producer_healthRecord.js`

## Acknowledgments 

This project uses the following open-source projects and dataset:

- Frontend is modified from [Nuxt UI Pro Dashboard template](https://github.com/nuxt-ui-pro/dashboard)
- Authentication module for the Nuxt frontend is modified from the [Nuxt3 example by Damien Heulin](https://github.com/damien-hl/nuxt3-auth-example)
- The electrocardiogram tracing dataset is taken from paper: [Ribeiro, A.H., Ribeiro, M.H., Paixão, G.M.M. et al. Automatic diagnosis of the 12-lead ECG using a deep neural network. Nat Commun 11, 1760 (2020).](https://zenodo.org/records/3765780)
