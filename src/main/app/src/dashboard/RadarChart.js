import React from "react";
import {Card, CardHeader} from "material-ui/Card";
import {translate} from "admin-on-rest";
import {Radar, RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis} from "recharts";
const data = [
    { subject: 'Math', A: 120, B: 110, fullMark: 150 },
    { subject: 'Chinese', A: 98, B: 130, fullMark: 150 },
    { subject: 'English', A: 86, B: 130, fullMark: 150 },
    { subject: 'Geography', A: 99, B: 100, fullMark: 150 },
    { subject: 'Physics', A: 85, B: 90, fullMark: 150 },
    { subject: 'History', A: 65, B: 85, fullMark: 150 },
];
const styles = {
    card: { borderLeft: '10px', flex: '21',marginRight: '1em' },
};
export default translate(({value, translate}) => (
    <Card style={styles.card}>
        <CardHeader title="统计报表"/>
        <RadarChart cx={200} cy={200} outerRadius={150} width={350} height={350} data={data}>
            <Radar name="Mike" dataKey="A" stroke="#8884d8" fill="#8884d8" fillOpacity={0.6}/>
            <PolarGrid />
            <PolarAngleAxis dataKey="subject" />
            <PolarRadiusAxis/>
        </RadarChart>
    </Card>
));