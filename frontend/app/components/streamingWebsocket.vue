
<template>
  <div id="main" style="width: 100%; height: 400px;"></div>

  <div id="metric">
    <p id ="heart_rate">Heart Rate:</p>
    <p id ="spo2">Spo2:</p>
    <p id ="blood_pressure">Blood Pressure:</p>
    <!-- <p id ="heart_rate_average">Average Heart Rate:</p>
    <p id ="spo2_average">Average Spo2:</p>
    <p id ="blood_pressure_average">Average Blood Pressure:</p> -->
  </div>

  <div id="metric_average">

  </div>

</template>
  
<script>
  import * as echarts from 'echarts';



  export default {
    name: 'EchartsComponent',
    props:{
        patient_id: {
            required: true
        }
    },
    data() {
      return {
        app: {},
        option: null,
        myChart: null,
        categories3: (function () {
            let now = new Date();
            let res = [];
            let len = 10;
            while (len--) {
                res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
                now = new Date(+now - 2.5);
            }
            return res;
        })(),
        data3:[],
        client_id: this.patient_id,
        data3_length: 0,
        categories3_length: 0,
        heart_rate: 0,
        spo2: 0,
        blood_pressure_systolic: 0,
        blood_pressure_diastolic:0,
        heart_rate_average: 0,
        spo2_average: 0,
        blood_pressure_systolic_average: 0,
        blood_pressure_diastolic_average: 0,
        record_type: ""
        }        
    },
    mounted() {    
      this.initChart();
      this.updateData();
      this.connectWebSocket();
      this.patient_id;
    },
    methods: {
      connectWebSocket() {        
        console.log( "id is: " + this.patient_id);
        
        let url_test = "ws://localhost:8081";
        let url_topic_healthRecord = `ws://localhost:8080/websocket/${this.patient_id}`;

        let webSocket = new WebSocket(url_topic_healthRecord);
        const axisData_ = new Date().toLocaleTimeString().replace(/^\D*/, '');
        webSocket.onmessage = evt => {
          console.log(evt.data);
          let health_record_JSON = JSON.parse(evt.data);  
                            
          // 0 for real time
          // 1 for average
          if (health_record_JSON.record_type == 0) {
            this.data3.push(health_record_JSON.ECG);
            this.categories3.push(axisData_);
                    
            this.heart_rate = health_record_JSON.heart_rate;
            this.spo2 = health_record_JSON.spo2;
            this.blood_pressure_diastolic = health_record_JSON.blood_pressure_diastolic;
            this.blood_pressure_systolic = health_record_JSON.blood_pressure_systolic;

            document.getElementById('heart_rate').textContent = "Heart Rate: " + this.heart_rate;
            document.getElementById('spo2').textContent = "Spo2: " + this.spo2;
            document.getElementById('blood_pressure').textContent = "Blood Pressure: " + this.blood_pressure_diastolic + " | " + this.blood_pressure_systolic;
          }
          // else if(health_record_JSON.record_type == 1){
          //   this.heart_rate_average = health_record_JSON.heart_rate;
          //   this.spo2_average = health_record_JSON.spo2;
          //   this.blood_pressure_diastolic_average = health_record_JSON.blood_pressure_diastolic_average;
          //   this.blood_pressure_systolic_average = health_record_JSON.blood_pressure_systolic_average;
          //   document.getElementById('heart_rate_average').textContent = "Average Heart Rate (last 15 mins): " + this.heart_rate_average;
          //   document.getElementById('spo2_average').textContent = "Average Spo2 (last 15 mins): " + this.spo2_average;
          //   document.getElementById('blood_pressure_average').textContent = "Average Blood Pressure (last 15 mins): " + this.blood_pressure_diastolic_average + " | " + this.blood_pressure_systolic_average;
          // }
          else{
            this.data3.push(0);
            this.categories3.push(axisData_);
          }
        }
      },
      initChart() {        
        const chartDom = document.getElementById('main');

        this.myChart = echarts.init(chartDom);


        this.option = {
          title: {
            text: 'ECG Plot'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#283b56'
              }
            }
          },
          legend: {},
          dataZoom: {
            show: false,
            start: 0,
            end: 100
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: true,
              data: this.categories3,
              axisLabel: { show: false }

            }
          ],
          yAxis: [
            {
                type: 'value',
                scale: true,
                name: 'Amplitude',
                max: 'dataMax',            
                min: 'dataMin',
                boundaryGap: [0.2, 0.2]
            }
          ],
          series: [
            {
              name: 'ECG',
              type: 'line',
              data: this.data3
            }
          ]
        };
        this.myChart.setOption(this.option);
      },
      updateData() {
        setInterval(() => {

        if(this.data3.length > 2900){
            let foo = this.data3.length - this.data3_length;                                                    
            this.data3 = this.data3.slice(foo,);
        }
        if(this.categories3.length > 2900){
            let foo2 = this.categories3.length - this.categories3_length;
            this.categories3 = this.categories3.slice(foo2,);
        }
        
        this.data3_length = this.data3.length;
        this.categories3_length = this.categories3.length;

        this.myChart.setOption({
        xAxis: [
            {
            data: this.categories3
            },
        ],
        series: [
            {
            data: this.data3,
            showSymbol: false,  
            },
            
        ]
        });
        }, 2.5);
      }
    }
  };
</script>