import React, { useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useDispatch } from 'react-redux';
import { getFollowerList } from '../../_actions/profileAction';

// components
import Follower from './Follower';

function test() {
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchFollowers = async () => {
      try {
        const jsonProfileId = localStorage.getItem('profileId');
        // const profileId = JSON.parse(jsonProfileId);
        const profileId = 20;
        await dispatch(getFollowerList(profileId)).then((res) => {});
      } catch (e) {
        console.log(e);
      }
    };
    fetchFollowers();
  }, []);
  return (
    <div>
      <h2> 아</h2>
    </div>
  );
}

export default test;
