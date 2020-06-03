import React from "react";
import {IMGSERVERURL} from "../.././/../common/Constant";

export default class ThumbImg extends React.Component {

    render() {
        const {src, alt} = this.props;
        return <div style={{width: '100px'}}>
            <img src={`${IMGSERVERURL}${src}`} alt={alt} style={{width: '100%', height: '100%'}}></img>
        </div>
    }
} 