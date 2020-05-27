import moment from "moment"

export const DateTimeFormat = (timestamp, formatStr = "YYYY-MM-DD HH:mm:ss") => {
    return moment(timestamp).format(formatStr);
}