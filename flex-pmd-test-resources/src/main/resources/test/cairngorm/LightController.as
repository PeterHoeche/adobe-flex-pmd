/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.scoyo.portal.control
{
import com.scoyo.portal.control.events.LoadUserProfileEvent;
import com.scoyo.portal.control.commands.LoadUserProfileCommand;
    import com.adobe.cairngorm.control.FrontController;
    import com.scoyo.platform.control.commands.community.LoadFavoriteModulesCommand;
    import com.scoyo.platform.control.commands.contentbrowsing.LoadBrowsableKnowledgeCommand;
    import com.scoyo.platform.control.commands.contentbrowsing.UpdateBrowsableKnowledgeCommand;
    import com.scoyo.platform.control.commands.core.InitializeApplicationCommand;
    import com.scoyo.platform.control.commands.learnmanagement.LoadExerciseContentTreeCommand;
    import com.scoyo.platform.control.commands.learnmanagement.LoadTrainingCenterStatisticCommand;
    import com.scoyo.platform.control.commands.notifications.LoadNotificationsCommand;
    import com.scoyo.platform.control.commands.personalrecord.LoadPersonalRecordCommand;
    import com.scoyo.platform.control.commands.personalrecord.UpdatePersonalRecordCommand;
    import com.scoyo.platform.control.events.community.LoadFavoriteModulesEvent;
    import com.scoyo.platform.control.events.contentbrowsing.LoadBrowsableKnowledgeEvent;
    import com.scoyo.platform.control.events.contentbrowsing.UpdateBrowsableKnowledgeEvent;
    import com.scoyo.platform.control.events.core.InitializeApplicationEvent;
    import com.scoyo.platform.control.events.learnmanagement.LoadExerciseContentTreeEvent;
    import com.scoyo.platform.control.events.learnmanagement.LoadTrainingCenterStatisticEvent;
    import com.scoyo.platform.control.events.notificationbox.LoadNotificationsEvent;
    import com.scoyo.platform.control.events.personalrecord.LoadPersonalRecordEvent;
    import com.scoyo.platform.control.events.personalrecord.UpdatePersonalRecordEvent;
    import com.scoyo.portal.control.commands.AcceptFriendshipCommand;
    import com.scoyo.portal.control.commands.CancelFriendshipCommand;
    import com.scoyo.portal.control.commands.DismissNotificationCommand;
    import com.scoyo.portal.control.commands.LoadKnowledgeTreeCommand;
    import com.scoyo.portal.control.commands.LoadRecentActivitiesCommand;
    import com.scoyo.portal.control.commands.RejectFriendshipCommand;
    import com.scoyo.portal.control.commands.ReloadBuddiesCommand;
    import com.scoyo.portal.control.commands.SaveMyProfileCommand;
    import com.scoyo.portal.control.commands.SaveUserCommand;
    import com.scoyo.portal.control.commands.SearchUsersCommand;
    import com.scoyo.portal.control.commands.SendFeedbackCommand;
    import com.scoyo.portal.control.commands.SendLogoutCommand;
    import com.scoyo.portal.control.commands.ShowHelpCommand;
    import com.scoyo.portal.control.commands.StartFriendshipCommand;
    import com.scoyo.portal.control.commands.URLLoaderCommand;
    import com.scoyo.portal.control.commands.XMLParseCommand;
    import com.scoyo.portal.control.events.AcceptFriendshipEvent;
    import com.scoyo.portal.control.events.CancelFriendshipEvent;
    import com.scoyo.portal.control.events.DismissNotificationEvent;
    import com.scoyo.portal.control.events.LoadKnowledgeTreeEvent;
    import com.scoyo.portal.control.events.LoadRecentActivitiesEvent;
    import com.scoyo.portal.control.events.RejectFriendshipEvent;
    import com.scoyo.portal.control.events.ReloadBuddiesEvent;
    import com.scoyo.portal.control.events.SaveMyProfileEvent;
    import com.scoyo.portal.control.events.SaveUserEvent;
    import com.scoyo.portal.control.events.SearchUsersEvent;
    import com.scoyo.portal.control.events.SendFeedbackEvent;
    import com.scoyo.portal.control.events.SendLogoutEvent;
    import com.scoyo.portal.control.events.ShowHelpEvent;
    import com.scoyo.portal.control.events.StartFriendshipEvent;
    import com.scoyo.portal.control.events.URLLoaderEvent;
    import com.scoyo.portal.control.events.XMLParseEvent;
    import com.scoyo.ecs.control.events.LoadArticleEvent;
    import com.scoyo.ecs.control.commands.LoadArticleCommand;
    import com.scoyo.ecs.control.commands.LoadMagazineCommand;
    import com.scoyo.ecs.control.events.LoadMagazineEvent;    

    /**
     * The main cairngorm controller. This defines the connections between the events and the commands 
     */
   public class Controller extends FrontController
   {
      public function Controller()
      {
         addCommand(
            InitializeApplicationEvent.EVENT_TYPE,
            InitializeApplicationCommand );
         addCommand(
            StartFriendshipEvent.EVENT_NAME,
            StartFriendshipCommand );
          addCommand(
            LoadNotificationsEvent.EVENT_TYPE,
            LoadNotificationsCommand );
          addCommand(
            DismissNotificationEvent.EVENT_NAME,
            DismissNotificationCommand );
         addCommand(
            AcceptFriendshipEvent.EVENT_NAME,
            AcceptFriendshipCommand );
	}

	private function initCommand1() : void
	{
         addCommand(
            CancelFriendshipEvent.EVENT_NAME,
            CancelFriendshipCommand );
         addCommand(
            RejectFriendshipEvent.EVENT_NAME,
            RejectFriendshipCommand );
         addCommand(
            SearchUsersEvent.EVENT_NAME,
            SearchUsersCommand );
//         addCommand(
//            LoadBuddiesEvent.EVENT_TYPE,
//            LoadBuddiesCommand );
         addCommand(
            LoadKnowledgeTreeEvent.EVENT_NAME,
            LoadKnowledgeTreeCommand );
	}

	private function initCommand1() : void
	{
         addCommand(
            LoadExerciseContentTreeEvent.EVENT_NAME,
            LoadExerciseContentTreeCommand );
         addCommand(
            LoadBrowsableKnowledgeEvent.EVENT_NAME,
            LoadBrowsableKnowledgeCommand );
         addCommand(
            UpdateBrowsableKnowledgeEvent.EVENT_NAME,
            UpdateBrowsableKnowledgeCommand );
          addCommand(
            ShowHelpEvent.EVENT_NAME,
            ShowHelpCommand );
          addCommand(
            URLLoaderEvent.EVENT_NAME,
            URLLoaderCommand );
	}

	protected function initCommand1() : void
	{
          addCommand(
            XMLParseEvent.EVENT_NAME,
            XMLParseCommand );
//          addCommand(
//            LoadAmbitiousUsersEvent.EVENT_TYPE,
//            LoadAmbitiousUsersCommand );
//          addCommand(
//            LoadNewUsersEvent.EVENT_TYPE,
//            LoadNewUsersCommand );
          addCommand(
            LoadFavoriteModulesEvent.EVENT_TYPE,
            LoadFavoriteModulesCommand );
          addCommand(
            SaveUserEvent.EVENT_NAME,
            SaveUserCommand );
	}

	private function initCommand1() : void
	{
          addCommand(
            SendLogoutEvent.EVENT_NAME,
            SendLogoutCommand );
          addCommand(
            SendFeedbackEvent.EVENT_NAME,
            SendFeedbackCommand );
          addCommand(
            LoadPersonalRecordEvent.EVENT_TYPE,
            LoadPersonalRecordCommand );
         addCommand(
            UpdatePersonalRecordEvent.EVENT_NAME,
            UpdatePersonalRecordCommand );
          addCommand(
            SaveMyProfileEvent.EVENT_NAME,
            SaveMyProfileCommand );
	}

	private function initCommand1() : void
	{
          addCommand(
            LoadUserProfileEvent.EVENT_NAME,
            LoadUserProfileCommand );
          addCommand(
            ReloadBuddiesEvent.EVENT_NAME,
            ReloadBuddiesCommand );
          addCommand(
            LoadRecentActivitiesEvent.EVENT_NAME,
            LoadRecentActivitiesCommand );
          addCommand(
            LoadTrainingCenterStatisticEvent.EVENT_NAME,
            LoadTrainingCenterStatisticCommand );
          addCommand(
             LoadMagazineEvent.EVENT_NAME,
             LoadMagazineCommand );
          addCommand(
             LoadArticleEvent.EVENT_NAME,
             LoadArticleCommand );
      }
   }
}