
<template>
    <div id="main" style="width: 100%; height: 400px;"></div>  
</template>
    
<script>
    import * as echarts from 'echarts';
  
  
    export default {
      name: 'EchartsComponent',
      props:{
          patient_id: {
            required: true,
          }, 
          timestamp: {
            required: true,
          }
      },
      data(){
        return{
            myChart: null,
            ecg_data_async: []
        }
      },
      async created() {        
        let url = `http://localhost:8080/patient/record/${this.patient_id}/${this.timestamp}`;
        const response = await fetch(url)
        const data = await response.json()
        this.ecg_data_async = data.ecgaggregation;
        this.updateChart();
      },
      mounted() {
        this.patient_id;
        this.timestamp;
        this.initChart();
      },
      methods: {
        initChart() {        
          const chartDom = document.getElementById('main');
          this.myChart = echarts.init(chartDom);
          this.option = {
            xAxis: [
              {
                type: 'category',
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
                data: this.ecg_data_async
              }
            ]
          };
          this.myChart.setOption(this.option);
        },
        updateChart(){                  
          this.option = {
            xAxis: [
              {
                type: 'category',                
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
                data: this.ecg_data_async
              }
            ]
          };
          this.myChart.setOption(this.option);
        }
      }
    };
</script>