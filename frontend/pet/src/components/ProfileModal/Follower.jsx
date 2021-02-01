import React from 'react';

function Follower(props) {
  const { followerId, nickname } = props.follower;
  return <li>{props.nickname}</li>;
}
export default Follower;
